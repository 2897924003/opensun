package article.inbound.co.article_management;

import article.inbound.co.CommandObject;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ArticleDeleteCO extends CommandObject {

    public long articleId;

    public ArticleDeleteCO() {
    }

    @Override
    public Long verifyCOSecurity() {
        Long authContext = (Long) RequestContextHolder.currentRequestAttributes().getAttribute("authContext", RequestAttributes.SCOPE_REQUEST);
        if (authContext == null || authContext != actorId) {
            throw new RuntimeException("你正在进行违法操作");
        }
        return authContext;
    }
}
