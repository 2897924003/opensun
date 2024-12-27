package ss.user.controller.ro;


import org.springframework.security.core.GrantedAuthority;
import ss.user.atcl.AuthConverter;
import ss.user.domain.User;
import ss.user.domain.UserSummary;

import java.time.LocalDateTime;
import java.util.List;

public record AccountRO(
        Long id,
        String username,
        String nickname,
        String avatar,
        List<String> authorities,
        LocalDateTime createTime,
        LocalDateTime updateTime,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean enabled
) {
    public static AccountRO create(User user, UserSummary userSummary) {
        return new AccountRO(
                user.getId(),
                user.getUsername(),
                userSummary.getNickname(),
                userSummary.getAvatar(),
                AuthConverter.mapBitToRole(user.getAuthorities()).stream().map(GrantedAuthority::getAuthority).toList(),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isEnabled()
        );
    }
}
