package ss.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_summary") // 指定表名
public class UserSummary {

    @Id
    private Long id;

    @Column(name = "nickname", nullable = true, unique = true)
    private String nickname;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    /**
     * 初始化用户的个性化信息。
     *
     * @return 初始化后的 UserSummary 对象
     */
    public static UserSummary of(Long userId) {
        UserSummary userSummary = new UserSummary();
        userSummary.setId(userId);
        userSummary.nickname = "OpenSun_%d".formatted(userSummary.id);
        userSummary.avatar = "https://loremflickr.com/400/400?lock=3511949300144017";
        return userSummary;
    }
}
