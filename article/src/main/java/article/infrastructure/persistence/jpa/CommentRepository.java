package article.infrastructure.persistence.jpa;

import article.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 评论仓储接口，Mapper的增强
 */
public interface CommentRepository extends JpaRepository<Comment, Comment.ID> {


    @Query(value = "SELECT * FROM article_comment WHERE article_id = :articleId AND level = :level AND parent_id = :parentId", nativeQuery = true)
    List<Comment> findCommentsN(
            @Param("articleId") long articleId,
            @Param("level") long level,
            @Param("parentId") long parentId,
            Pageable pageable
    );


}
