package article.atcl.auth.policy;

import org.springframework.stereotype.Service;

@Service
public class AttributeBasedPolicy implements AuthorizePolicy {
    @Override
    public boolean authorize(String accessToken) {
        return false;
    }

    @Override
    public boolean authorize(Object... context) {
        return AuthorizePolicy.super.authorize(context);
    }
}
