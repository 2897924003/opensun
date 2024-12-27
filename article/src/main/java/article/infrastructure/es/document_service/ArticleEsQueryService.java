package article.infrastructure.es.document_service;

import article.inbound.ro.ApiRO;
import article.infrastructure.es.document_repository.ArticleIndexRepository;
import article.infrastructure.es.index.ArticleIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章索引下文档的查操作
 */
@Service
public class ArticleEsQueryService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleIndexRepository articleIndexRepository;

    /**
     * 根据标题分词查询文章
     *
     * @param title 标题
     * @param page  页码从0开始
     * @param size  大小
     * @return
     */
    public ApiRO<List<ArticleIndex>> searchByTitle(String title, int page, int size) {
        SearchPage<ArticleIndex> byTitle = articleIndexRepository.findByTitle(title, PageRequest.of(page, size));
        // 提取查询结果
        List<ArticleIndex> articles = byTitle.stream()
                .map((SearchHit::getContent))
                .collect(Collectors.toList());
        return ApiRO.success(articles);
    }


}
