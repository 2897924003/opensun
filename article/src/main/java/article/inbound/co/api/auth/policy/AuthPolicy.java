package article.inbound.co.api.auth.policy;

public interface AuthPolicy {
    String getBindGenerics();

    boolean authorize();
}
