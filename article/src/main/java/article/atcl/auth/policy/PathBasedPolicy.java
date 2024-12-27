package article.atcl.auth.policy;

import org.springframework.stereotype.Service;

@Service
public class PathBasedPolicy implements AuthorizePolicy {
    @Override
    public boolean authorize(String accessToken) {
        return false;
    }
}
