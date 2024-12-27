package article.inbound.co.comment;

import article.domain.Comment;

public record CommentVoteCO(
        Comment.ID id,
        Long delta) {
    public CommentVoteCO {
        if (delta > 0) {
            delta = 1L;
        } else if (delta < 0) {
            delta = -1L;
        }
    }
}
