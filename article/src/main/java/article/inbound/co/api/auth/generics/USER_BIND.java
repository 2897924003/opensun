package article.inbound.co.api.auth.generics;

//调用者提供自身ID(简化的ROLE)
public class USER_BIND implements GenericsPolicy {
    public static final String POLICY = "USER_BIND";

    @Override
    public String getPolicy() {
        return POLICY;
    }
}
