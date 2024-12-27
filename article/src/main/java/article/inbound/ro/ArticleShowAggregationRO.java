package article.inbound.ro;

import article.domain.Article;
import article.inbound.dto.ArticleContent;


/**
 * 文章聚合信息
 */
public record ArticleShowAggregationRO(
        Article article,
        ArticleContent articleContent) {
}
