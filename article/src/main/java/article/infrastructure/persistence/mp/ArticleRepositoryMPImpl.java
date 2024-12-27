package article.infrastructure.persistence.mp;

import article.domain.Article;
import article.domain.UserSummary;
import article.inbound.dto.ArticleContent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleRepositoryMPImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleRepository {


    @Override
    public List<Article> query(long userId, int current, int size, boolean isPublished) {
        // 创建分页对象
        Page<Article> page = new Page<>(current, size);

        // 查询用户关联的文章 ID
        List<Long> articleIds = baseMapper.query(userId);

        if (articleIds.isEmpty()) {
            return List.of();
        }

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getId, articleIds);
        queryWrapper.eq(Article::getIsPublished, isPublished);

        return baseMapper.selectPage(page, queryWrapper).getRecords();
    }


    @Override
    public void saveArticleContent(ArticleContent articleContent) {
        baseMapper.saveArticleContent(articleContent);
    }

    @Override
    public void removeArticleContent(long articleId) {
        baseMapper.removeArticleContent(articleId);
    }

    @Override
    public void removeUserArticle(long articleId) {
        baseMapper.removeUserArticle(articleId);
    }

    @Override
    public void updateArticleContent(ArticleContent articleContent) {
        baseMapper.updateArticleContent(articleContent);
    }

    @Override
    public ArticleContent getArticleContent(long id) {
        return baseMapper.getArticleContent(id);
    }

    @Override
    public void addComment(long articleId, long userId, String comment) {
        baseMapper.addComment(articleId, userId, comment);
    }


    @Override
    public Long countComment() {
        return baseMapper.countComment();
    }

    @Override
    public UserSummary findUser(Long userId) {
        return baseMapper.findUserSummaryById(userId);
    }

    @Override
    public void saveComment(long articleId, long parentId, long level, long userId, String comment) {
        baseMapper.saveComment(articleId, parentId, level, userId, comment);
    }


    @Override
    public void saveUserArticle(long userId, long articleId) {
        baseMapper.saveUserArticle(userId, articleId);
    }


}
