package article.statistic;

/**
 * 将一段时间的交互数据缓存起来，定时更新数据库与实时缓存数据
 */
public enum InteractionCacheNameSpec {
    AGGREGATION_ID("article:interaction:aggregation:"),
    AGGREGATION("article:interaction:aggregation"),
    VIEW("views"),
    VOTE("votes"),
    COMMENT("comments"),
    SHARE("shares"),
    COLLECT("collects"),
    ;


    private final String nameSpec;

    /**
     * 获取命名空间中的id部分
     *
     * @param aggregation ***:**:ID 获取ID
     * @return id
     */
    public static Long getAggregationId(String aggregation) {
        String[] split = aggregation.split(":");
        return Long.valueOf(split[split.length - 1]);
    }

    InteractionCacheNameSpec(String key) {
        this.nameSpec = key;
    }

    /**
     * 重写为打印命名空间名，而非枚举名
     *
     * @return 命名空间
     */
    @Override
    public String toString() {
        return nameSpec;
    }
}
