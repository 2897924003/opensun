package article.atcl.auth.policy;

/**
 * 授权策略
 */
public interface AuthorizePolicy {
    /**
     * 授权
     *
     * @param accessToken 常见依据JWT
     * @return 授权结果
     */
    boolean authorize(String accessToken);

    default long authorize(String accessToken, Boolean needActorId) {
        return 0L;
    }

    ;

    default boolean authorize(Object... context) {
        return false;
    }

    default String getPolicy() {
        return "";
    }
}
