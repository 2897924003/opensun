package article.inbound.co.api.auth.policy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PolicyRepository {

    private final Map<String, AuthPolicy> repository = new HashMap<>();

    public PolicyRepository(List<AuthPolicy> authPolicies) {
        authPolicies.forEach(
                policy ->
                        repository.put(policy.getBindGenerics(), policy));
    }

    public AuthPolicy getPolicy(String bindGenerics) {
        return repository.get(bindGenerics);
    }

}
