package article.inbound.dto;

import article.domain.Article;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章元数据：封装文章不能自动生成的基本信息与。
 */
@Data
public class ArticleMeta {
    private Long authorId;
    private String authorImg;
    private String authorName;
    private String img;
    private String title;
    private String summary;
    private String link;

    public ArticleMeta() {
    }

    public ArticleMeta(Long authorId, String authorImg, String authorName, String img, String title, String summary, String link) {
        this.authorId = authorId;
        this.authorImg = authorImg;
        this.authorName = authorName;
        this.img = img;
        this.title = title;
        this.summary = summary;
        this.link = link;
        System.out.println("ArticleMeta...");
    }

    public Article toEntity() {
        return new Article(null, authorId, authorImg, authorName, title, img, summary, null, 0L, 0L, 0L, 0L, 0L, 0L, LocalDateTime.now(), LocalDateTime.now(), false);
    }
}
