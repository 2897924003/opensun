package article.inbound.ro;


import article.domain.Comment;
import article.domain.UserSummary;


public record CommentViewAggregationRO(
        Comment comment,
        UserSummary userSummary) {
}
