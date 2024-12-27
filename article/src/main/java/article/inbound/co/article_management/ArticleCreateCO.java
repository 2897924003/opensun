package article.inbound.co.article_management;


import article.inbound.dto.ArticleContent;
import article.inbound.dto.ArticleMeta;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * DDD指令对象-封装业务上下文与业务行为-数据传输+业务控制指令
 */
public record ArticleCreateCO(
        ArticleContent articleContent,
        ArticleMeta articleMeta,
        long actorId
) {
    public ArticleCreateCO {
        System.out.println("ArticleCreateCO...");
    }

    /**
     * 验证指令安全
     */
    public Long verifyCOSecurity() {
        Long authContext = (Long) RequestContextHolder.currentRequestAttributes().getAttribute("authContext", RequestAttributes.SCOPE_REQUEST);
        System.out.println(authContext);
        System.out.println(actorId);
        System.out.println(articleMeta.getAuthorId());
        if (authContext == null || authContext != actorId || actorId != articleMeta.getAuthorId()) {
            throw new RuntimeException("你正在进行违法操作");
        }
        return authContext;
    }

}
