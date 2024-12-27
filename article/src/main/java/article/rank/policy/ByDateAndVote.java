package article.rank.policy;

import article.domain.Article;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ByDateAndVote extends ArticleScoreCalculator {

    protected ByDateAndVote() {
        super(RankNameSpec.ARTICLE_RANK_DATE_VOTE.toString());
    }

    @Override
    public void calculate(Article article) {
        long votes = article.getVotes() * 8640;
        long seconds = Duration.between(article.getPublishDate(), LocalDateTime.now()).toSeconds();
        long score = votes - seconds;
        article.setScore(score > 0 ? score : 0);
    }
}
