package article.usecase;

import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.generics.USER_BIND;
import article.inbound.co.article_interaction.CommentCO;
import article.inbound.co.article_interaction.CommonInteractionCO;
import article.inbound.ro.ApiRO;
import article.infrastructure.persistence.mp.ArticleRepository;
import article.statistic.ArticleStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户与文章模型互动用例层，负责协调各设施，服务。
 */
@Service
public class ArticleInteractionUseCase {

    private final ArticleStatisticsService articleStatisticsService;
    private final ArticleRepository articleRepository;

    public ArticleInteractionUseCase(ArticleStatisticsService articleStatisticsService, ArticleRepository articleRepository) {
        this.articleStatisticsService = articleStatisticsService;
        this.articleRepository = articleRepository;
    }

    /**
     * 点赞/点踩
     * <p>无失败处理策略</p>
     */
    public ApiRO<Void> vote(CommonInteractionCO co) {
        //安全检查
        co.verifyCOSecurity();
        //统计服务-vote
        articleStatisticsService.recordVote(co.articleId, co.actorId);
        return ApiRO.success();

    }

    /**
     * 收藏/取消收藏
     */
    public void collect(CommonInteractionCO co) {
        //安全检查
        co.verifyCOSecurity();
        //统计服务-collect
        articleStatisticsService.recordCollect(co.articleId, co.actorId);
    }

    /**
     * 评论
     */
    @Transactional
    public ApiRO<Void> comment(ApiAuthCO<CommentCO, USER_BIND> co) {
        //提取业务对象
        CommentCO commentCO = co.co;
        //统计服务-comment
        articleStatisticsService.recordComment(Long.parseLong(commentCO.articleId()), co.getActorId());
        //仓储服务-更新评论。
        articleRepository.addComment(Long.parseLong(commentCO.articleId()), co.getActorId(), commentCO.comment());
        return ApiRO.success("评论成功！");

    }

    /**
     * 分享
     */
    public void share(CommonInteractionCO co) {
        //安全检查
        co.verifyCOSecurity();
        //统计服务-share
        articleStatisticsService.recordShare(co.articleId, co.actorId);
    }


}
