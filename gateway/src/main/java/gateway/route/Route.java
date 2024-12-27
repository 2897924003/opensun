package gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class Route {


    @Bean
    public RouteLocator RequestModifier(RouteLocatorBuilder builder) {
        return builder.routes()
                //认证中心服务路由
                .route(p -> p
                        .path("/cas/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://cas"))

                //消息通知服务路由
                .route(p -> p
                        .path("/message/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://message-service"))

                //代码编译运行服务路由
                .route(p -> p
                        .path("/code/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://code-service"))

                //代码编译运行服务路由
                .route(p -> p
                        .path("/article/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://article-service"))


                //前端服务器路由
                .route(p -> p
                        .path("/**")
                        .uri("http://localhost:9000/"))

                .build();
    }

}
