package article.inbound.co.api.auth;

import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.policy.AuthPolicy;
import article.inbound.co.api.auth.selector.GenericsSelector;
import article.usecase.exception.ApiCONotPermittedException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import static article.inbound.co.api.ApiAuthCO.APICO_AUTH_CONTEXT;

@Service
public class ApiAuthManager {
    //自身引用
    public static ApiAuthManager instance;

    private final GenericsSelector selector;

    public ApiAuthManager(GenericsSelector policySelector) {
        this.selector = policySelector;
    }

    //在 Spring 完成依赖注入并将 Bean 放入容器后执行
    @PostConstruct
    public void init() {
        instance = this;
    }

    public void auth() {
        ApiAuthCO apiCOTEST = APICO_AUTH_CONTEXT.get();
        AuthPolicy policy = selector.select(apiCOTEST.getPolicy());
        boolean isAuthorized = policy.authorize();

        if (!isAuthorized) {
            throw new ApiCONotPermittedException("授权未通过");
        }

    }
}
