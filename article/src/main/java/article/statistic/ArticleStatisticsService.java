package article.statistic;


public interface ArticleStatisticsService {

    enum InteractionType {
        VISIT, LIKE, DISLIKE, COMMENT, COLLECT
    }

    /**
     * 方案评估中
     */
    default void record(ArticleInteractionEO eo, InteractionType interactionType) {
        switch (interactionType) {
            case VISIT:
                recordVisit(1L);
        }
    }

    /**
     * 👁️记录访问
     */
    void recordVisit(long articleId);

    /**
     * 记录点赞👍/点踩👎
     */
    void recordVote(long articleId, Long actorId);

    /**
     * 记录评论
     */
    void recordComment(long articleId, Long actorId);

    /**
     * 记录收藏
     */
    void recordCollect(long articleId, Long actorId);

    /**
     * 记录分享
     */
    void recordShare(long articleId, Long actorId);
}
