package article.infrastructure.persistence.mp;

import article.domain.Article;
import article.domain.UserSummary;
import article.inbound.dto.ArticleContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleRepository extends IService<Article> {


    /**
     * 分页查询
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    List<Article> query(long userId, int page, int size, boolean isPublished);

    void saveArticleContent(ArticleContent articleContent);

    void saveUserArticle(long userId, long articleId);

    void removeArticleContent(long articleId);

    void removeUserArticle(long articleId);

    void updateArticleContent(ArticleContent articleContent);

    ArticleContent getArticleContent(long id);

    void addComment(long articleId, long userId, String comment);


    Long countComment();

    UserSummary findUser(Long userId);

    void saveComment(long articleId, long parentId, long level, long userId, String comment);


}
