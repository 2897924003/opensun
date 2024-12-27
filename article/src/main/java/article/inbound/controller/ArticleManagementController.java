package article.inbound.controller;

import article.atcl.auth.service.RequireAuthorization;
import article.domain.Article;
import article.inbound.co.article_management.ArticleCreateCO;
import article.inbound.co.article_management.ArticleDeleteCO;
import article.inbound.co.article_management.ArticleEditCO;
import article.inbound.co.article_management.ArticlePublishCO;
import article.inbound.ro.ApiRO;
import article.usecase.ArticleManagementUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章管理相关服务接口
 */
@RestController
@RequestMapping("/articles")
public class ArticleManagementController {

    /**
     * DI：将文章管理应用层对象，从IOC容器中取出，通过构造函数引用注入控制器对象.
     * <p>
     * 由于文章管理业务用例较为固定，故并没有设计接口运用多态，仅仅运用了IOC
     * </p>
     */
    private final ArticleManagementUseCase articleManagementUseCase;

    public ArticleManagementController(ArticleManagementUseCase articleManagementUseCase) {
        this.articleManagementUseCase = articleManagementUseCase;
    }

    /**
     * 创建文章草稿
     *
     * @param co 创建文章命令对象
     * @return 成功响应
     */
    @RequireAuthorization(policy = RequireAuthorization.AuthorizationPolicy.ROLE)
    @PostMapping("/create")
    public ApiRO<Void> createArticle(@RequestBody ArticleCreateCO co) {
        articleManagementUseCase.createArticle(co);

        return ApiRO.success();
    }

    /**
     * 发布文章
     *
     * @param co 发布文章命令对象
     */
    @PostMapping("/publish")
    @RequireAuthorization(policy = RequireAuthorization.AuthorizationPolicy.ROLE)
    public ResponseEntity<ApiRO<Void>> publishArticle(@RequestBody ArticlePublishCO co) {
        articleManagementUseCase.publishArticle(co);
        return ResponseEntity.ok(ApiRO.success());
    }

    /**
     * 删除文章
     *
     * @param co 删除文章命令对象
     * @return 成功响应
     */
    @PostMapping("/delete")
    @RequireAuthorization(policy = RequireAuthorization.AuthorizationPolicy.ROLE)
    public ResponseEntity<ApiRO<Void>> deleteArticle(@RequestBody ArticleDeleteCO co) {
        articleManagementUseCase.deleteArticle(co);
        return ResponseEntity.ok(ApiRO.success());
    }

    /**
     * 编辑文章
     *
     * @param co 编辑文章命令对象
     * @return 成功响应
     */
    @PutMapping("/edit")
    @RequireAuthorization(policy = RequireAuthorization.AuthorizationPolicy.ROLE)
    public ResponseEntity<ApiRO<Void>> editArticle(@RequestBody ArticleEditCO co) {
        articleManagementUseCase.editArticle(co);
        return ResponseEntity.ok(ApiRO.success());
    }

    /**
     * 查询已发布的文章（分页）
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页条数
     * @return 已发布的文章列表
     */
    @GetMapping("/published")
    public ResponseEntity<List<Article>> queryPublishedArticles(
            @RequestParam("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        List<Article> articles = articleManagementUseCase.queryPublishedArticles(userId, page, size);
        return ResponseEntity.ok(articles);
    }

    /**
     * 查询未发布的文章（分页）
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页条数
     * @return 草稿文章列表
     */
    @GetMapping("/unpublished")
    @RequireAuthorization(policy = RequireAuthorization.AuthorizationPolicy.ROLE)
    public ResponseEntity<List<Article>> queryUnPublishedArticles(
            @RequestParam("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        List<Article> articles = articleManagementUseCase.queryUnPublishedArticles(userId, page, size);
        return ResponseEntity.ok(articles);
    }

}
