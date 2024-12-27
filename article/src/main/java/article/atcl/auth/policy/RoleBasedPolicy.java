package article.atcl.auth.policy;

import article.atcl.auth.outbound.AuthorizeServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleBasedPolicy implements AuthorizePolicy {

    @Autowired
    private AuthorizeServiceClient feign;


    @Override
    public boolean authorize(String accessToken) {
        return Boolean.parseBoolean(feign.authorize(accessToken));
    }

    @Override
    public long authorize(String accessToken, Boolean needActorId) {
        String bauthorize = feign.bauthorize(accessToken);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bauthorize, Long.class);
        } catch (Exception e) {
            // Handle exception
        }
        return 1L;
    }
}
