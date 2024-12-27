package article.rank;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("article_rank")

public class ArticleRank {

    @TableId
    private Long id;

    private Integer score;

    private String link;

    private String tag;

}
