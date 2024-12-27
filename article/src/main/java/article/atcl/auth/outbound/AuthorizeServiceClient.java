package article.atcl.auth.outbound;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 基于http协议实现的RESTFul风格的RPC远程过程调用
 */
@FeignClient(name = "cas")
public interface AuthorizeServiceClient {

    @PostMapping("/authorize")
    String authorize(@RequestBody String accessToken);

    @PostMapping("/authorize/actorid")
    String bauthorize(@RequestBody String accessToken);
}
