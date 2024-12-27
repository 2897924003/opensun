package article.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("article_meta")
public class Article {

    /**
     * 文章唯一ID，自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 作者头像
     */
    private String authorImg;
    /**
     * 作者名称
     */
    private String authorName;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章封面图的URL
     */
    private String img;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章链接
     */
    private String link;
    /**
     * 文章评分
     */
    private Long score;
    /**
     * 文章浏览次数
     */
    private Long views;
    /**
     * 文章点赞数
     */
    private Long votes;
    /**
     * 文章评论量
     */
    private Long comments;
    /**
     * 文章分享量
     */
    private Long shares;
    /**
     * 文章收藏量
     */
    private Long collects;
    /**
     * 文章发布时间
     */
    private LocalDateTime publishDate;
    /**
     * 文章编辑时间，表示最后一次编辑时间
     */
    private LocalDateTime editDate;
    /**
     * 发布状态，true表示已发布，false表示未发布
     */
    @TableField("is_published")
    private Boolean isPublished;

    public Article() {
    }

    public Article(Long id, Long authorId, String authorImg, String authorName, String title, String img, String summary, String link, Long score, Long views, Long votes, Long comments, Long shares, Long collects, LocalDateTime publishDate, LocalDateTime editDate, Boolean isPublished) {
        this.id = id;
        this.authorId = authorId;
        this.authorImg = authorImg;
        this.authorName = authorName;
        this.title = title;
        this.img = img;
        this.summary = summary;
        this.link = link;
        this.score = score;
        this.views = views;
        this.votes = votes;
        this.comments = comments;
        this.shares = shares;
        this.collects = collects;
        this.publishDate = publishDate;
        this.editDate = editDate;
        this.isPublished = isPublished;
    }

    /**
     * 更新浏览量
     *
     * @param delta Δ
     */
    public void updateViews(Long delta) {
        if (delta == null) {
            return;
        }
        views += delta;
    }

    /**
     * 更新点赞量
     *
     * @param delta Δ
     */
    public void updateVotes(Long delta) {
        if (delta == null) {
            return;
        }
        votes += delta;
    }

    /**
     * 更新分享量
     *
     * @param delta Δ
     */
    public void updateShares(Long delta) {
        if (delta == null) {
            return;
        }
        shares += delta;
    }

    /**
     * 更新收藏量
     *
     * @param delta Δ
     */
    public void updateCollects(Long delta) {
        if (delta == null) {
            return;
        }
        collects += delta;
    }

    /**
     * 更新评论量
     *
     * @param delta Δ
     */
    public void updateComments(Long delta) {
        if (delta == null) {
            return;
        }
        comments += delta;
    }

    /*----------------------------------------------------------------------------*/
}
