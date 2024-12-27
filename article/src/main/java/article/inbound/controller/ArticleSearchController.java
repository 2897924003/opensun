package article.inbound.controller;


import article.inbound.ro.ApiRO;
import article.infrastructure.es.document_service.ArticleEsQueryService;
import article.infrastructure.es.index.ArticleIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章查询服务接口
 */
@RestController
@RequestMapping("/es")
public class ArticleSearchController {

    @Autowired
    private ArticleEsQueryService articleEsQueryService;

    /**
     * 根据标题分词查询文章
     *
     * @param title 标题
     * @param page  页码从0开始
     * @param size  大小
     * @return
     */
    @GetMapping("/{title}")
    private ResponseEntity<ApiRO<List<ArticleIndex>>> searchByTitle(
            @PathVariable("title") String title,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity.ok(articleEsQueryService.searchByTitle(title, page, size));
    }


}
