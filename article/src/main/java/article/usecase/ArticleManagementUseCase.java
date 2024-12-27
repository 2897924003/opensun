package article.usecase;

import article.atcl.auth.service.AuthorizeUtils;
import article.domain.Article;
import article.inbound.co.article_management.ArticleCreateCO;
import article.inbound.co.article_management.ArticleDeleteCO;
import article.inbound.co.article_management.ArticleEditCO;
import article.inbound.co.article_management.ArticlePublishCO;
import article.infrastructure.persistence.mp.ArticleRepository;
import article.rank.InRedisArticleRanker;
import article.rank.policy.RankNameSpec;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DDD-应用层-文章管理-用户管理文章服务
 */
@Service
public class ArticleManagementUseCase {
    private final ArticleRepository articleRepository;
    private final InRedisArticleRanker inRedisArticleRanker;

    private final ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public ArticleManagementUseCase(ArticleRepository articleRepository, InRedisArticleRanker inRedisArticleRanker) {
        this.articleRepository = articleRepository;
        this.inRedisArticleRanker = inRedisArticleRanker;
    }


    /**
     * 创建文章草稿
     */
    @Transactional
    public void createArticle(ArticleCreateCO co) {
        co.verifyCOSecurity();
        // 更新 article_meta 表
        Article entity = co.articleMeta().toEntity();

        articleRepository.save(entity);
        //获取文章主键
        co.articleContent().id = entity.getId();

        // 更新 article_content 表
        virtualExecutor.execute(() -> articleRepository.saveArticleContent(co.articleContent()));
        // 更新 user_article 表
        virtualExecutor.execute(() -> articleRepository.saveUserArticle(co.actorId(), entity.getId()));


    }

    /**
     * 发布文章
     */
    public void publishArticle(ArticlePublishCO co) {
        co.verifyCOSecurity();
        //从仓储中获得对应文章
        Article article = articleRepository.getById(co.articleId);
        //将文章设置为已发布,调用仓储进行持久化
        article.setIsPublished(true);
        articleRepository.saveOrUpdate(article);
        //选择要加入的排行榜，将文章加入排行榜
        //TODO 事件发布或消息发布
        inRedisArticleRanker.update(article, RankNameSpec.ARTICLE_RANK_DATE_VOTE.toString());

    }

    /**
     * 删除文章
     */
    @Transactional
    public void deleteArticle(ArticleDeleteCO co) {
        //检查要删除的文章是否属于指令发起人
        co.verifyCOSecurity();
        //删除article_meta
        virtualExecutor.execute(() -> articleRepository.removeById(co.articleId));
        //删除article_content
        virtualExecutor.execute(() -> articleRepository.removeArticleContent(co.articleId));
        //删除user_article
        virtualExecutor.execute(() -> articleRepository.removeUserArticle(co.articleId));
        //退出所有排行榜，采用消息队列完成；
        //TODO 事件发布或消息发布
        virtualExecutor.execute(() -> inRedisArticleRanker.delete(co.articleId, RankNameSpec.ARTICLE_RANK_DATE_VOTE.toString()));

    }

    /**
     * 文章修改
     */
    @Transactional
    public void editArticle(ArticleEditCO co) {
        co.verifyCOSecurity();
        //更新article_content表
        articleRepository.updateArticleContent(co.articleContent);
        //查找原文章互动元数据
        Article meta = articleRepository.getById(co.articleContent.id);
        //覆盖旧数据
        BeanUtils.copyProperties(co.articleMeta, meta);
        meta.setEditDate(LocalDateTime.now());
        virtualExecutor.execute(() -> articleRepository.saveOrUpdate(meta));
    }

    /**
     * 查询已发布文章
     */
    public List<Article> queryPublishedArticles(long userId, int page, int size) {
        return articleRepository.query(userId, page, size, true);
    }

    /**
     * 查询未发布的文章
     */
    public List<Article> queryUnPublishedArticles(long userId, int page, int size) {
        AuthorizeUtils.commonVerify(userId);
        return articleRepository.query(userId, page, size, false);
    }


}
