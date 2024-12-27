package ss.springsecurity.ohs;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ss.springsecurity.utils.JwtUtils;
import ss.user.atcl.AuthConverter;
import ss.user.domain.User;
import ss.user.infrastructure.persistance.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class MaintainLoginController {

    //先凑合写了
    @Autowired
    private UserRepository userRepository;

    //TODO 完善
    //这里先直接查数据库了，后面可以用redis集成管理会话，
    // 前提是找到轻松容拓展的自定义默认的authentication，因为默认信息不足以支撑业务需求
    @PostMapping("/maintainLogin")
    public void maintainLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String accessToken = request.getHeader("access_token");

            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        accessToken = cookie.getValue();
                        break;
                    }
                }
            }

            //如果还是null，说明用户未登录，直接返回即可
            if (accessToken == null) {
                return;
            }

            DecodedJWT decodedJWT = JwtUtils.verifyToken(accessToken);
            System.out.println(decodedJWT.getClaim("userId").toString());
            Long userId = JwtUtils.verifyToken(accessToken).getClaim("userId").asLong();
            Optional<User> byId = userRepository.findById(userId);
            User user = byId.get();
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(userId));
            map.put("username", user.getUsername());
            map.put("nickname", user.getUsername());//凑合了
            map.put("img", "https://loremflickr.com/400/400?lock=8723139437331451");
            List<String> list = AuthConverter.mapBitToRole(user.getAuthorities()).stream().map(GrantedAuthority::getAuthority).toList();
            map.put("role", list.toString());
            String s = objectMapper.writeValueAsString(map);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(s);
        } catch (Exception e) {

        }


    }
}
