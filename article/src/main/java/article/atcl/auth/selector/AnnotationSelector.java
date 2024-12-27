package article.atcl.auth.selector;

import article.atcl.auth.policy.AttributeBasedPolicy;
import article.atcl.auth.policy.AuthorizePolicy;
import article.atcl.auth.policy.PathBasedPolicy;
import article.atcl.auth.policy.RoleBasedPolicy;
import article.atcl.auth.service.RequireAuthorization;
import org.springframework.stereotype.Component;


/**
 * 基于注解的授权策略选择器。
 */
@Component
public class AnnotationSelector implements PolicySelector {

    private final RoleBasedPolicy roleBasedPolicy;
    private final PathBasedPolicy pathBasedPolicy;
    private final AttributeBasedPolicy attributeBasedPolicy;

    private AnnotationSelector(RoleBasedPolicy roleBasedPolicy, PathBasedPolicy pathBasedPolicy, AttributeBasedPolicy attributeBasedPolicy) {
        this.roleBasedPolicy = roleBasedPolicy;
        this.pathBasedPolicy = pathBasedPolicy;
        this.attributeBasedPolicy = attributeBasedPolicy;
    }

    @Override
    public AuthorizePolicy select(Object... factor) {
        if (factor[0] instanceof RequireAuthorization requireAuthorization) {
            RequireAuthorization.AuthorizationPolicy policy = requireAuthorization.policy();
            switch (policy) {
                case ROLE -> {
                    return roleBasedPolicy;
                }
                case PATH -> {
                    return pathBasedPolicy;
                }
                case ABAC -> {
                    return attributeBasedPolicy;
                }
            }
        }

        return null;
    }


}
