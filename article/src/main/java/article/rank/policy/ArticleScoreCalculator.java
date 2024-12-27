package article.rank.policy;

import article.domain.Article;

/**
 * 计算特定排行榜下，文章排序分计算策略基类：
 */
public abstract class ArticleScoreCalculator {

    public final String rank;

    protected ArticleScoreCalculator(String rank) {
        this.rank = rank;
    }

    public abstract void calculate(Article article);


}
