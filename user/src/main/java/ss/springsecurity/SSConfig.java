package ss.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import ss.springsecurity.cors.ACorsConfigurationSource;
import ss.springsecurity.formlogin.FormLoginConfig;
import ss.springsecurity.oauth2login.Oauth2LoginAuthenticationFailureHandler;
import ss.springsecurity.oauth2login.Oauth2LoginAuthenticationSuccessHandler;
import ss.springsecurity.utils.SecurityConfigUtil;

@EnableWebSecurity(debug = true)
@Configuration
@SuppressWarnings("unchecked")//去除removeConfigurer警告
public class SSConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*==================移除过滤器==================*/

        // (DefaultLoginPageGeneratingFilter,DefaultLogoutPageGeneratingFilter)
        //前后端分离，移除默认登录登出页面
        http.removeConfigurer(DefaultLoginPageConfigurer.class);

        //(CsrfFilter)
        //前后端分离，关闭csrf防护
        http.csrf(AbstractHttpConfigurer::disable);

        //无状态架构，不在请求间持久化security context,关闭这个后其实等于不管理session，不会有securitycontext了
        //http.securityContext(AbstractHttpConfigurer::disable);

        //无状态架构，不管理session
        //http.sessionManagement(AbstractHttpConfigurer::disable);

        //无需匿名用户
        http.removeConfigurer(AnonymousConfigurer.class);

        /*==================添加过滤器==================*/

        //AuthorizationFilter
        //请求授权策略
        http.authorizeHttpRequests(config -> config
                .anyRequest().permitAll()
        );


        //(CorsFilter)
        //cors策略配置
        http.cors(config -> config.configurationSource(ACorsConfigurationSource.getInstance()));

        //(Oauth2AuthenticationFilter)
        //oauth2登录
        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .successHandler(new Oauth2LoginAuthenticationSuccessHandler())
                        .failureHandler(new Oauth2LoginAuthenticationFailureHandler())
                );

        //(UsernamePasswordAuthenticationFilter)
        // 表单登录认证策略
        SecurityConfigUtil.config(http, new FormLoginConfig());

        return http.build();
    }
}
