package article.rank;


import article.domain.Article;

/**
 * 文章排序初始化
 */
public interface ArticleRanker {
    void rankAll(Article article);

    void update(Article article, String rank);

    void delete(long articleId, String rank);


}
