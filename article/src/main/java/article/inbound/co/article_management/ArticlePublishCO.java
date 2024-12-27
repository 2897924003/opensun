package article.inbound.co.article_management;

import article.inbound.co.CommandObject;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ArticlePublishCO extends CommandObject {

    public long articleId;

    public ArticlePublishCO() {
    }

    @Override
    public Long verifyCOSecurity() {
        Long authContext = (Long) RequestContextHolder.currentRequestAttributes().getAttribute("authContext", RequestAttributes.SCOPE_REQUEST);
        System.out.println(authContext);
        System.out.println(actorId);
        System.out.println(articleId);
        if (authContext == null || authContext != actorId) {
            throw new RuntimeException("你正在进行违法操作");
        }
        return authContext;
    }
}
