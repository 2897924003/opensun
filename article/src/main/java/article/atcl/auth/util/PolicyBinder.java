package article.atcl.auth.util;


import article.atcl.auth.policy.AuthorizePolicy;

/**
 * 策略绑定工具类
 * <p>
 * 具体功能：将业务上下文环境与授权策略绑定并执行策略
 */
public class PolicyBinder {

    /**
     * 基于JWT
     *
     * @param policy
     * @param
     * @return
     */
    public static boolean bindAndExecute(AuthorizePolicy policy, String accessToken) {
        return policy.authorize(accessToken);
    }

    /**
     * 基于JWT
     *
     * @param policy
     * @param
     * @return
     */
    public static Long bindAndExecute(AuthorizePolicy policy, String accessToken, boolean needActorId) {
        return policy.authorize(accessToken, needActorId);
    }
}
