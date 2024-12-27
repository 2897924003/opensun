package article.rank.policy;

import article.domain.Article;
import org.springframework.stereotype.Component;

/**
 * 按照点赞数计算文章排序分
 */
@Component
public class ByVotes extends ArticleScoreCalculator {

    public ByVotes() {
        super(RankNameSpec.ARTICLE_RANK_VOTE.toString());
    }

    @Override
    public void calculate(Article article) {
        article.setScore(article.getVotes());
    }
}
