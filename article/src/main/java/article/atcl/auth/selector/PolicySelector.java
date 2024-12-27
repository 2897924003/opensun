package article.atcl.auth.selector;


import article.atcl.auth.policy.AuthorizePolicy;

/**
 * 授权策略选择器
 */
public interface PolicySelector {
    /**
     * 根据具体业务上下文，选择授权策略。
     *
     * @param factor 策略选择因子
     * @return 具体授权策略
     */
    AuthorizePolicy select(Object... factor);

    static AuthorizePolicy select(PolicySelector policy, Object... factor) {
        return policy.select(factor);
    }
}
