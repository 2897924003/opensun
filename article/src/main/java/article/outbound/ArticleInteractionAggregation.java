package article.outbound;

import lombok.Data;

@Data
public class ArticleInteractionAggregation {
    private Long articleId;
    public Long views;
    public Long votes;
    public Long comments;
    public Long collects;
    private Long shares;

    public ArticleInteractionAggregation() {
    }

    public ArticleInteractionAggregation(Long articleId, Long views, Long votes) {
        this.articleId = articleId;
        this.views = views;
        this.votes = votes;
    }

}
