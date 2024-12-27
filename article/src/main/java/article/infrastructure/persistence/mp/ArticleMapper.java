package article.infrastructure.persistence.mp;

import article.domain.Article;
import article.domain.UserSummary;
import article.inbound.dto.ArticleContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询某个用户的所有文章ID
     *
     * @param userId 用户ID
     * @return 用户拥有的文章ID列表
     */
    @Select("select article_id from user_article where user_id=#{userId}")
    List<Long> query(Long userId);

    /**
     * 保存文章内容
     *
     * @param ac 文章实体，包含文章内容
     */
    @Insert("INSERT INTO article_content (article_id, content) VALUES (#{id}, #{content})")
    void saveArticleContent(ArticleContent ac);


    /**
     * 记录用户与文章的关联
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     */
    @Insert("INSERT INTO user_article (user_id, article_id) VALUES (#{userId}, #{articleId})")
    void saveUserArticle(@Param("userId") long userId, @Param("articleId") long articleId);

    @Delete("delete from article_content where article_id=#{articleId}")
    void removeArticleContent(@Param("articleId") long articleId);

    @Delete("delete from user_article where article_id=#{articleId}")
    void removeUserArticle(@Param("articleId") long articleId);

    @Update("update article_content set content = #{content} where article_id=#{id}")
    void updateArticleContent(ArticleContent articleContent);

    @Select("select article_content.content from article_content where article_id=#{id}")
    ArticleContent getArticleContent(@Param("id") long id);

    @Insert("insert into article_comment (article_id,user_id,comment,level) value (#{articleId},#{userId},#{comment},0)")
    void addComment(@Param("articleId") long articleId, @Param("userId") long userId, @Param("comment") String comment);

    @Select("SELECT COUNT(*) FROM article_comment")
    Long countComment();

    @Select("select * from user_summary where id =  #{userId}")
    UserSummary findUserSummaryById(@Param("userId") Long userId);

    @Insert("insert into article_comment " +
            "(article_id,parent_id, level, user_id, comment) " +
            "VALUES (#{articleId},#{parentId},#{level},#{userId},#{comment}) ")
    void saveComment(@Param("articleId") long articleId, @Param("parentId") long parentId, @Param("level") long level, @Param("userId") long userId, @Param("comment") String comment);

}
