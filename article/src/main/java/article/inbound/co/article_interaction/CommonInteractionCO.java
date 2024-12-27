package article.inbound.co.article_interaction;

import article.inbound.co.CommandObject;

public class CommonInteractionCO extends CommandObject {
    public Long articleId;

    @Override
    public Long verifyCOSecurity() {
        return null;
    }
}
