package article.inbound.co.api.auth.selector;


import article.inbound.co.api.auth.generics.GenericsPolicy;
import article.inbound.co.api.auth.policy.AuthPolicy;

public interface Selector {

    /**
     * 根据ApiCO容器POLICY泛型类型选择认证策略
     *
     * @param policy 策略选择因子
     * @return 具体授权策略
     */
    AuthPolicy select(GenericsPolicy policy);


}
