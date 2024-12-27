package article.inbound.co.api;

import article.inbound.co.api.auth.ApiAuthManager;
import article.inbound.co.api.auth.generics.GenericsPolicy;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiAuthCO<T, POLICY extends GenericsPolicy> {
    //完成本次调用的基本参数
    public final T co;
    //身份凭证：JWT
    public final String token;


    //调用者ID
    private Long actorId;
    //授权策略，后端选择
    private POLICY policy;

    private static final ApiAuthManager authManager = ApiAuthManager.instance;
    public static final ScopedValue<ApiAuthCO> APICO_AUTH_CONTEXT = ScopedValue.newInstance();

    //参数绑定
    @JsonCreator
    public ApiAuthCO(
            @JsonProperty("token") String token,
            @JsonProperty("co") T co,
            @JsonProperty("policy") POLICY policy
    ) {
        this.token = token;
        this.co = co;
        this.policy = policy;
        authBoot(); // 自动触发认证
    }

    public void authBoot() {
        ScopedValue.runWhere(APICO_AUTH_CONTEXT, this, authManager::auth);
    }


}
