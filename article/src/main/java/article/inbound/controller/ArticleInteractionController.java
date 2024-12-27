package article.inbound.controller;

import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.generics.USER_BIND;
import article.inbound.co.article_interaction.CommentCO;
import article.inbound.co.article_interaction.CommonInteractionCO;
import article.inbound.ro.ApiRO;
import article.usecase.ArticleInteractionUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章互动服务接口
 */
@RestController
@RequestMapping("/article")
public class ArticleInteractionController {
    private final ArticleInteractionUseCase articleInteractionUseCase;

    public ArticleInteractionController(ArticleInteractionUseCase articleInteractionUseCase) {
        this.articleInteractionUseCase = articleInteractionUseCase;
    }

    /**
     * 用户发起点赞/点踩
     *
     * @param co 【用户标识，文章标识】
     */
    @PostMapping("/vote")
    public ApiRO<Void> vote(@RequestBody CommonInteractionCO co) {
        return articleInteractionUseCase.vote(co);
    }

    /**
     * 用户对文章评论
     *
     * @param co 业务对象
     */
    @PostMapping("/comment")
    public ApiRO<Void> comment(@RequestBody ApiAuthCO<CommentCO, USER_BIND> co) {
        return articleInteractionUseCase.comment(co);
    }

    /**
     * 用户对文章收藏
     *
     * @param co 业务对象
     */
    @PostMapping("/collect")
    public void collect(@RequestBody CommonInteractionCO co) {
        articleInteractionUseCase.collect(co);
    }

    /**
     * 用户对文章分享
     *
     * @param co 业务对象
     */
    @PostMapping("/share")
    public void share(@RequestBody CommonInteractionCO co) {
        articleInteractionUseCase.share(co);
    }
}
