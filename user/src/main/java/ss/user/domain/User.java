package ss.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //getter，Setter
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Entity //实体
@Table(name = "user") // 指定表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "authorities")
    private int authorities;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public static User create(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

        return user;
    }

}
