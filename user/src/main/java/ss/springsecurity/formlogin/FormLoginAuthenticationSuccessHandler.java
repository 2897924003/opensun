package ss.springsecurity.formlogin;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import ss.springsecurity.utils.JwtUtils;
import ss.user.controller.ro.ApiRO;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 设置返回的响应状态码
        response.setStatus(HttpServletResponse.SC_OK);
        // 设置响应的内容类型为JSON
        response.setContentType("application/json;charset=UTF-8");
        // 根据用户的目标系统生成访问令牌
        String system = request.getHeader("system");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String name = authentication.getName();
        String jwt = JwtUtils.generateToken(system, name, authorities);
        response.setHeader("access_token", jwt);
        //response.addCookie(new Cookie("access_token", jwt));//TODO 全部改用set-cookie
        // 获取认证的用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        FormLoginRO formLoginRO = new FormLoginRO(userDetails, (Long) RequestContextHolder.currentRequestAttributes().getAttribute("userId", 0));

        //转化为统一响应格式
        ApiRO<FormLoginRO> apiRO = ApiRO.success(formLoginRO);
        //序列化
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonApiRO = objectMapper.writeValueAsString(apiRO);
        //写入响应体
        response.getWriter().write(jsonApiRO);

       /* //执行Oauth2认证成功的重定向操作
        if (authentication.getAuthorities().toString().contains("OAUTH2_USER")){
            response.sendRedirect("https://115.120.246.30/Oauth2WaitingPage?email=2897924003@qq.com");
        }
*/
        clearAuthenticationAttributes(request);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    private record FormLoginRO(
            Long id,
            String username,
            String nickname,
            String avatar,
            List<String> role
    ) {
        FormLoginRO(UserDetails userDetails, Long id) {
            this(id, userDetails.getUsername(), userDetails.getUsername(), "https://loremflickr.com/400/400?lock=8723139437331451", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        }
    }


}
