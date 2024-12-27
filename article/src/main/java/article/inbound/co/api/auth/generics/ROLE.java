package article.inbound.co.api.auth.generics;

import lombok.Data;

@Data
public class ROLE implements GenericsPolicy {
    public static final String POLICY = "ROLE";

    @Override
    public String getPolicy() {
        return POLICY;
    }
}
