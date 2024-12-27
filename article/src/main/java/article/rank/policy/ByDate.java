package article.rank.policy;

import article.domain.Article;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 * 按照日期计算文章排序分
 */
@Component
public class ByDate extends ArticleScoreCalculator {

    public ByDate() {
        super(RankNameSpec.ARTICLE_RANK_DATE.toString());
    }

    @Override
    public void calculate(Article article) {
        article.setScore(Duration.between(article.getPublishDate(), LocalDateTime.now()).toSeconds());
    }

}
