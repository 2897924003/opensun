package article.rank;

import article.domain.Article;
import article.infrastructure.es.document_service.ArticleEsCommandService;
import article.infrastructure.es.index.ArticleIndex;
import article.rank.policy.ArticleScoreCalculator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InRedisArticleRanker implements ArticleRanker {

    private final RedisTemplate<String, Long> redisRankTemplate;
    private final List<ArticleScoreCalculator> calculators;
    @Autowired
    ArticleEsCommandService articleEsCommandService;


    public InRedisArticleRanker(RedisTemplate<String, Long> redisTemplate, List<ArticleScoreCalculator> calculators) {
        this.redisRankTemplate = redisTemplate;
        this.calculators = calculators;
    }


    @Override
    public void rankAll(Article article) {
        ArticleIndex articleIndex = new ArticleIndex();
        BeanUtils.copyProperties(article, articleIndex);
        articleEsCommandService.addDocument(articleIndex);
        //TODO 临时这样更新redis与es
        calculators.forEach(calculator -> {
            calculator.calculate(article);
            redisRankTemplate.opsForZSet()
                    .add(
                            calculator.rank,
                            article.getId(),
                            article.getScore());

        });
    }

    @Override
    public void update(Article article, String rank) {
        redisRankTemplate.opsForZSet().add(rank, article.getId(), article.getScore());
    }

    @Override
    public void delete(long articleId, String rank) {
        redisRankTemplate.opsForZSet().remove(rank, articleId);
    }


}
