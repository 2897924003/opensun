package article.inbound.co.article_interaction;

public record CommentCO(
        String articleId,
        String parentId,
        Long level,
        String comment) {


}
