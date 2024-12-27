package article.inbound.co.api.auth.selector;

import article.inbound.co.api.auth.generics.GenericsPolicy;
import article.inbound.co.api.auth.policy.AuthPolicy;
import article.inbound.co.api.auth.policy.PolicyRepository;
import org.springframework.stereotype.Service;

@Service
public class GenericsSelector implements Selector {

    private final PolicyRepository repository;

    public GenericsSelector(PolicyRepository repository) {
        this.repository = repository;
    }

    public AuthPolicy select(GenericsPolicy policy) {
        return repository.getPolicy(policy.getPolicy());
    }

}
