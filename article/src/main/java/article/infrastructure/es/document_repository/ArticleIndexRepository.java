
package article.infrastructure.es.document_repository;

import article.infrastructure.es.index.ArticleIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * ArticleIndex索引下的文档仓储
 */
public interface ArticleIndexRepository extends ElasticsearchRepository<ArticleIndex, Long> {
    @Query("{\"match\": {\"title\": {\"query\": \"?0\"}}}")
    SearchPage<ArticleIndex> findByTitle(String name, Pageable pageable);
}

