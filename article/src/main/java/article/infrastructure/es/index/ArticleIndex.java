
package article.infrastructure.es.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 声明索引，不存在则创建
 */
@Document(indexName = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleIndex {

    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private Long authorId;

    @Field(type = FieldType.Keyword, index = false)
    private String authorImg;

    @Field(type = FieldType.Keyword)
    private String authorName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String summary;

    @Field(type = FieldType.Keyword, index = false)
    private String img;

    @Field(type = FieldType.Keyword, index = false)
    private String link;

    @Field(type = FieldType.Integer)
    private Long score;

    @Field(type = FieldType.Integer)
    private Long views;

    @Field(type = FieldType.Integer)
    private Long votes;

    @Field(type = FieldType.Integer)
    private Long comments;

    @Field(type = FieldType.Integer)
    private Long collects;

    @Field(type = FieldType.Integer)
    private Long shares;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime editDate;

    @Field(type = FieldType.Boolean, index = false)
    private Boolean isPublished;

}

