package ss.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class UserInfo {
    @Id
    private Long id;

    private String nickname;

    private String avatar;

    private Short gender;

    private LocalDate birth;

    private String province;

    private String identity;


    public static UserInfo of(Long id) {
        return new UserInfo();
    }
}
