package article.outbound;

import article.domain.Article;
import article.infrastructure.persistence.mp.ArticleRepository;
import article.rank.ArticleRankProcessManager;
import article.statistic.InteractionCacheNameSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleInteractionStatisticsUpdater {
    private final ArticleRepository articleRepository;
    public final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ArticleRankProcessManager articleRankProcessManager;

    public ArticleInteractionStatisticsUpdater(ArticleRepository articleRepository, StringRedisTemplate stringRedisTemplate, ArticleRankProcessManager articleRankProcessManager) {
        this.articleRepository = articleRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.articleRankProcessManager = articleRankProcessManager;
    }

    /**
     * 定时进行数据更新
     */
    @Scheduled(fixedRate = 30000)
    public void updateInteractionData() {

        if (shouldSyncDataFromRedis()) {
            syncAndPersistenceInteractionDataFromRedis();
        }
    }

    /**
     * 根据<strong> 策略 </strong>判断是否需要进行 <strong>持久化</strong> 与 <strong>实时同步</strong>
     * <p>目前策略：互动数据总条数>10</p>
     */
    private boolean shouldSyncDataFromRedis() {
        Long size = stringRedisTemplate.opsForSet().size(InteractionCacheNameSpec.AGGREGATION.toString());
        return size != null && size > 0;
    }

    /**
     * <strong>获取缓存数据 </strong>进行<strong> 持久化 与 实时同步 </strong>操作
     */
    private void syncAndPersistenceInteractionDataFromRedis() {
        //获取互动数据集合下的全部文章互动元聚合标识
        List<String> aggregations = stringRedisTemplate.opsForSet().pop(InteractionCacheNameSpec.AGGREGATION.toString(), Long.MAX_VALUE);
        if (aggregations == null || aggregations.isEmpty()) {
            return;
        }
        //定义待持久化文章集合，后续得以实现批量更新，提高效率
        List<Article> articlesToUpdate = new ArrayList<>();
        //遍历文章互动元聚合标识列表
        for (String aggregation : aggregations) {
            //获取当前互动元聚合下的全部互动元
            Map<Object, Object> metas = stringRedisTemplate.opsForHash().entries(aggregation);
            //提取互动元聚合标识中的文章Id
            metas.put("articleId", InteractionCacheNameSpec.getAggregationId(aggregation));
            //将互动元聚合映射到aia中
            ArticleInteractionAggregation aia = objectMapper.convertValue(metas, ArticleInteractionAggregation.class);
            //调用仓储服务，获取待更新的文章
            Article article = articleRepository.getById(aia.getArticleId());
            article.updateViews(aia.getViews());
            article.updateVotes(aia.getVotes());
            article.updateComments(aia.getComments());
            article.updateCollects(aia.getCollects());
            article.updateShares(aia.getShares());
            //加入待持久化文章集合
            articlesToUpdate.add(article);
        }
        // 批量更新数据库，并删除散列
        articleRepository.saveOrUpdateBatch(articlesToUpdate);
        stringRedisTemplate.delete(aggregations);

        //TODO 这里先直接调用排行榜初始化方法
        articleRankProcessManager.rank();

        // TODO: 更新实时缓存
    }

}

