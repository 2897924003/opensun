package article.usecase;

import article.domain.Article;
import article.inbound.dto.ArticleContent;
import article.inbound.ro.ArticleShowAggregationRO;
import article.infrastructure.persistence.mp.ArticleRepository;
import article.rank.RankCommand;
import article.rank.policy.RankNameSpec;
import article.statistic.ArticleStatisticsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DDD-应用层-文章分发-用户阅读文章服务
 */
@Service
public class ArticleDistributeUseCase {

    private final ArticleRepository articleRepository;
    private final ArticleStatisticsService articleStatisticsService;
    private final RedisTemplate<String, Long> redisRankTemplate;

    public ArticleDistributeUseCase(ArticleRepository articleRepository, ArticleStatisticsService articleStatisticsService, RedisTemplate<String, Long> redisRankTemplate) {
        this.articleRepository = articleRepository;
        this.articleStatisticsService = articleStatisticsService;
        this.redisRankTemplate = redisRankTemplate;
    }

    /**
     * 文章推送
     *
     * @param co 推送指令对象
     */
    public void distribute(RankCommand co) {

        //解析命令对象-发起人-目标...

        //从redis中查看是否存在文章
        //1.不存在：说明redis失败。（启动应用前应执行 分数更新，缓存初始化）
        //2.存在：
        // 1. 选择推送引擎
        //- 使用默认推送引擎(无差异推送-排名）

        //- 使用个性化推送引擎

    }

    /**
     * 精确查找文章展示聚合信息-文章区+评论区
     *
     * @param articleId
     * @return
     */
    public ArticleShowAggregationRO byId(long articleId) {
        // 首先查看一级缓存（LRU,每篇被访问的文章，初始都会设置一个TTL3,如果没被访问就-1，被访问就加1，如果为0，超过100）中是否有，再查看二级缓存中是否有

        //调用仓储服务，获取文章展示信息，并聚合。
        Article article = articleRepository.getById(articleId);
        //判断是否是已发布文章，否则不予返回
        if (!article.getIsPublished()) {
            return null;
        }
        ArticleContent articleContent = articleRepository.getArticleContent(articleId);

        //调用文章统计服务-访问记录-有刷浏览量风险
        articleStatisticsService.recordVisit(articleId);
        //articleStatisticsService.record(new ArticleInteractionEO(), ArticleStatisticsService.InteractionType.VIEW);

        return new ArticleShowAggregationRO(article, articleContent);
    }

    public List<Article> byDateAndVote(Page<Article> page) {

        //从redis里获取默认排行榜的文章集（一篇文章想要被优先展示，需要获取足够点赞，抵消时间衰减）
        Set<Long> id = redisRankTemplate.opsForZSet()

                .reverseRangeByScore(RankNameSpec.ARTICLE_RANK_DATE_VOTE.toString(),
                        0L,
                        Long.MAX_VALUE
                        , page.offset()
                        , page.getSize());


        if (id.isEmpty()) {
            return Collections.emptyList();
        }
        //调用仓储服务，获取对应文章
        List<Article> articles = articleRepository.listByIds(id);

        // 按 Redis 排序结果对查询结果进行排序
        Map<Long, Article> articleMap = articles.stream()
                .collect(Collectors.toMap(Article::getId, Function.identity()));

        return id.stream()
                .map(articleMap::get)
                .filter(Objects::nonNull)
                .toList();

    }


}
