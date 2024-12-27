package article.statistic;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultArticleStatisticsService implements ArticleStatisticsService {

    private final StringRedisTemplate stringRedisTemplate;

    public DefaultArticleStatisticsService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 记录访问量
     */
    @Override
    public void recordVisit(long articleId) {
        //将文章互动数据加入散列中，统一管理各项数据，便于后续统一进行更新任务。
        stringRedisTemplate.opsForHash().increment(
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId,
                InteractionCacheNameSpec.VIEW.toString(),
                1L
        );
        recordToRedisSet(articleId);
    }

    /**
     * 记录点赞量
     */
    @Override
    public void recordVote(long articleId, Long actorId) {

        stringRedisTemplate.opsForHash().increment(
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId,
                InteractionCacheNameSpec.VOTE.toString(),
                1L
        );
        recordToRedisSet(articleId);
    }

    /**
     * 记录评论量
     */
    @Override
    public void recordComment(long articleId, Long userId) {
        stringRedisTemplate.opsForHash().increment(
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId,
                InteractionCacheNameSpec.COMMENT.toString(),
                1L
        );
        recordToRedisSet(articleId);
    }

    /**
     * 记录收藏量
     */
    @Override
    public void recordCollect(long articleId, Long userId) {

        stringRedisTemplate.opsForHash().increment(
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId,
                InteractionCacheNameSpec.COLLECT.toString(),
                1L
        );
        recordToRedisSet(articleId);
    }

    /**
     * 记录分享量
     */
    @Override
    public void recordShare(long articleId, Long actorId) {

        stringRedisTemplate.opsForHash().increment(
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId,
                InteractionCacheNameSpec.SHARE.toString(),
                1L
        );
        recordToRedisSet(articleId);
    }

    /**
     * 将待更新互动数据的文章加入redis中，<strong>可改为集齐一批，批量加入</strong>
     */
    private void recordToRedisSet(long articleId) {
        stringRedisTemplate.opsForSet().add(
                InteractionCacheNameSpec.AGGREGATION.toString(),
                InteractionCacheNameSpec.AGGREGATION_ID.toString() + articleId);
    }
}
