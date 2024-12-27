package article.atcl.auth;

public interface Authenticatable {
    default boolean authenticate() {
        return true;
    }
}
