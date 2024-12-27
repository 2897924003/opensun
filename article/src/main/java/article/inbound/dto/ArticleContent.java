package article.inbound.dto;

import lombok.Data;

/**
 * 文章内容：封装文章的正文部分
 */
@Data
public class ArticleContent {
    public Long id;
    public String content;
}
