package article.atcl.auth.service;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequireAuthorization {

    enum AuthorizationPolicy {
        ROLE,
        PATH,
        ABAC,
        UserBound
    }

    AuthorizationPolicy policy() default AuthorizationPolicy.ROLE;

}
