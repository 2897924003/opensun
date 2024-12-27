package article.inbound.controller;

import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.generics.USER_BIND;
import article.inbound.co.article_interaction.CommentCO;
import article.inbound.co.comment.CommentVoteCO;
import article.inbound.ro.ApiRO;
import article.inbound.ro.CommentViewAggregationRO;
import article.inbound.ro.PageMetaRO;
import article.usecase.CommentSectionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论区服务接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    /**
     * 应用层接口
     */
    @Autowired
    private CommentSectionUseCase commentSectionUseCase;

    /**
     * 查询评论
     *
     * @param articleId 所属文章
     * @param page      页数
     * @param size      页面大小
     * @param level     层级
     * @param parentId  所属评论
     */
    @GetMapping("/{articleId}")
    public ApiRO<PageMetaRO<CommentViewAggregationRO>> findAllComment(
            @PathVariable("articleId") long articleId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("level") long level,
            @RequestParam("parentId") long parentId,
            @RequestParam("sort") List<String> sort
    ) {
        System.out.println(sort);
        return commentSectionUseCase.findComments(articleId, page, size, level, parentId, sort);
    }


    /**
     * 评论
     *
     * @param co 业务对象
     */
    @PostMapping("/comment")
    public ApiRO<Void> comment(@RequestBody ApiAuthCO<CommentCO, USER_BIND> co) {
        return commentSectionUseCase.makeComment(co);
    }


    /**
     * 点赞/点踩
     *
     * @param co 业务对象
     */
    @PostMapping("/vote")
    public ApiRO<Void> vote(@RequestBody ApiAuthCO<CommentVoteCO, USER_BIND> co) {
        return commentSectionUseCase.vote(co);
    }


}
