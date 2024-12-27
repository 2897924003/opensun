package article.infrastructure.es.document_service;

import article.infrastructure.es.document_repository.ArticleIndexRepository;
import article.infrastructure.es.index.ArticleIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章索引下文档的增删改操作
 */
@Service
public class ArticleEsCommandService {
    @Autowired
    private ArticleIndexRepository articleIndexRepository;


    public void addDocument(ArticleIndex article) {
        articleIndexRepository.save(article);
    }

    public void deleteDocument(ArticleIndex article) {
        articleIndexRepository.delete(article);
    }

    public void updateDocument(ArticleIndex article) {
        articleIndexRepository.save(article);
    }

}
