package article.atcl.auth.service;


import article.atcl.auth.policy.AuthorizePolicy;
import article.atcl.auth.selector.PolicySelector;
import article.atcl.auth.util.PolicyBinder;
import article.inbound.co.ApiCO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class AuthorizationAspect {
    public static final ScopedValue<Boolean> auth = ScopedValue.newInstance();


    private final PolicySelector policySelector;

    private AuthorizationAspect(PolicySelector policySelector) {
        this.policySelector = policySelector;
    }

    @Around("@annotation(article.atcl.auth.service.RequireAuthorization)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取当前请求，响应
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //尝试获取代理方法的参数
        Object[] args = joinPoint.getArgs();
        ApiCO<?> apiCo = Arrays.stream(args)
                .filter(arg -> arg instanceof ApiCO<?>)
                .map(arg -> (ApiCO<?>) arg)
                .findFirst().orElse(null);


        //使用反射获取注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireAuthorization annotation = method.getAnnotation(RequireAuthorization.class);

        String accessToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            accessToken = Arrays.stream(cookies)
                    .filter(cookie -> "access_token".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (accessToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        //根据注解声明授权方式获取授权策略
        AuthorizePolicy authorizePolicy = PolicySelector.select(policySelector, annotation);

        Long actorId = PolicyBinder.bindAndExecute(authorizePolicy, accessToken, true);
        //使用策略绑定工具执行具体授权策略
        if (!PolicyBinder.bindAndExecute(authorizePolicy, accessToken)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }


        if (apiCo != null) {
            apiCo.setActorId(actorId);
            apiCo.setPermitted(true);
        }
        //将业务所需授权相关上下文存入请求上下文中
        RequestContextHolder.currentRequestAttributes().setAttribute("authContext", actorId, RequestAttributes.SCOPE_REQUEST);


        return joinPoint.proceed();
    }


}
