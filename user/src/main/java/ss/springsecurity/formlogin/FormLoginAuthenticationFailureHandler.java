package ss.springsecurity.formlogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ss.user.controller.ro.ApiRO;

import java.io.IOException;

public class FormLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ApiRO<Void> apiRO = ApiRO.fail(exception.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonApiRO = objectMapper.writeValueAsString(apiRO);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //写入响应体
        response.getWriter().write(jsonApiRO);
    }

}
