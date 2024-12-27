package article.statistic;

/**
 * 用户与文章交互产生的事件对象
 */
public class ArticleInteractionEO {
    public long articleId;
    public Long userId;  // 可选，某些事件可能不需要用户 ID
    public ArticleStatisticsService.InteractionType interactionType;  // 记录交互类型（如访问、点赞、评论等）


}

