package article.domain;

import article.infrastructure.utils.SortUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_comment") // 指定表名
@Data
public class Comment {
    @Embeddable
    public record ID(
            @Column(name = "article_id") Long articleId,
            @Column(name = "comment_id") Long commentId) {
        public static Comment.ID of(String articleId, String commentId) {
            return new Comment.ID(Long.parseLong(articleId), Long.parseLong(commentId));
        }

        //避免前端大数问题
        public String getArticleId() {
            return String.valueOf(articleId);
        }

        //避免前端大数问题
        public String getCommentId() {
            return String.valueOf(commentId);
        }
    }

    @EmbeddedId
    private ID ID;

    private String parentId;

    private Long level;

    private Long userId;

    private String comment;

    private Long votes;

    private Long comments;

    private LocalDateTime ct;

    private LocalDateTime ut;


    /**
     * 更新点赞量
     *
     * @param delta Δ
     */
    public void updateVotes(Long delta) {
        if (delta == null) {
            return;
        }
        votes += delta;
    }

    /**
     * 更新评论量
     *
     * @param delta Δ
     */
    public void updateComments(Long delta) {
        if (delta == null) {
            return;
        }
        comments += delta;
    }

    //排列组合+防注入
    public enum CommentSortableFields implements SortUtil.SortableFields {
        votes,
        comments,
        ct;
    }

}
