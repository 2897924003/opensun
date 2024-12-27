package article.inbound.co.article_management;

import article.inbound.co.CommandObject;
import article.inbound.dto.ArticleContent;
import article.inbound.dto.ArticleMeta;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ArticleEditCO extends CommandObject {

    public ArticleMeta articleMeta;
    public ArticleContent articleContent;

    public ArticleEditCO() {
    }

    @Override
    public Long verifyCOSecurity() {
        Long authContext = (Long) RequestContextHolder.currentRequestAttributes().getAttribute("authContext", RequestAttributes.SCOPE_REQUEST);
        if (authContext == null || authContext != actorId) {
            throw new RuntimeException("你正在进行违法操作");
        }
        return authContext;
    }


    public ArticleMeta getArticleMeta() {
        return articleMeta;
    }

    public void setArticleMeta(ArticleMeta articleMeta) {
        this.articleMeta = articleMeta;
    }

    public ArticleContent getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(ArticleContent articleContent) {
        this.articleContent = articleContent;
    }

}
