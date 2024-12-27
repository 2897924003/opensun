package article.usecase;

import article.domain.Article;
import article.domain.Comment;
import article.domain.UserSummary;
import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.generics.USER_BIND;
import article.inbound.co.article_interaction.CommentCO;
import article.inbound.co.comment.CommentVoteCO;
import article.inbound.ro.ApiRO;
import article.inbound.ro.CommentViewAggregationRO;
import article.inbound.ro.PageMetaRO;
import article.infrastructure.persistence.jpa.CommentRepository;
import article.infrastructure.persistence.mp.ArticleRepository;
import article.infrastructure.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 评论区服务用例
 */
@Service
public class CommentSectionUseCase {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    private Long totalElements;

    @Scheduled(fixedRate = 1000 * 60 * 2)
    public void cache() {
        totalElements = articleRepository.countComment();
    }

    /**
     * 查询评论
     *
     * @param articleId  所属文章
     * @param page       页数
     * @param size       页面大小
     * @param level      层级
     * @param parentId   所属评论
     * @param properties 排序
     */
    public ApiRO<PageMetaRO<CommentViewAggregationRO>> findComments(long articleId, int page, int size, long level, long parentId, List<String> properties) {

        Sort sort = SortUtil.createSort(Comment.CommentSortableFields.class, properties, Sort.Order::desc);

        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);

        List<Comment> comments = commentRepository.findCommentsN(articleId, level, parentId, pageRequest);
        PageMetaRO<CommentViewAggregationRO> ro = PageMetaRO.getListElementsAndConvert(comments,
                comment -> {
                    UserSummary user = articleRepository.findUser(comment.getUserId());
                    return new CommentViewAggregationRO(comment, user);
                }
        );
        ro.setCurrentPage(page);
        ro.setTotalElements(totalElements);
        return ApiRO.success(ro);
    }

    /**
     * 评论
     *
     * @param apiCO 业务对象
     */
    public ApiRO<Void> makeComment(ApiAuthCO<CommentCO, USER_BIND> apiCO) {
        CommentCO co = apiCO.co;
        //存储评论//TODO 改为使用评论仓储
        articleRepository.saveComment(Long.parseLong(co.articleId()), Long.parseLong(co.parentId()), co.level(), apiCO.getActorId(), co.comment());

        //更新文章元数据
        Article article = articleRepository.getById(co.articleId());
        article.updateComments(1L);
        articleRepository.saveOrUpdate(article);

        //更新父评论状态
        if (co.level() != 0) {
            Optional<Comment> optComment = commentRepository.findById(Comment.ID.of(co.articleId(), co.parentId()));
            optComment.ifPresent(comment -> {
                comment.updateComments(1L);
                commentRepository.save(comment);

            });
        }
        return ApiRO.success("评论成功");
    }

    /**
     * 点赞
     *
     * @param apiCO 业务对象
     */
    public ApiRO<Void> vote(ApiAuthCO<CommentVoteCO, USER_BIND> apiCO) {
        return commentRepository.findById(apiCO.co.id())
                .map(comment -> {
                    if (comment.getVotes() + apiCO.co.delta() < 0) {
                        return ApiRO.success();
                    }
                    comment.updateVotes(apiCO.co.delta());
                    commentRepository.save(comment);
                    return ApiRO.success("成功");

                })
                .orElse(ApiRO.fail("评论不存在"));
    }


}
