package article.inbound.co.api.auth.policy;

import article.inbound.co.api.ApiAuthCO;
import article.inbound.co.api.auth.generics.USER_BIND;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import static article.inbound.co.api.ApiAuthCO.APICO_AUTH_CONTEXT;

@Service
public class UserBindAuthPolicy implements AuthPolicy {
    public static final String BIND_GENERICS = USER_BIND.POLICY;

    @Override
    public String getBindGenerics() {
        return BIND_GENERICS;
    }

    //本地直接解析JWT,获取用户ID即可
    @Override
    public boolean authorize() {
        ApiAuthCO apiCOTEST = APICO_AUTH_CONTEXT.get();
        try {
            DecodedJWT decodedJWT = verifyToken(apiCOTEST.getToken());
            apiCOTEST.setActorId(Long.valueOf(decodedJWT.getClaim("userId").toString()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //调用认证中心进行授权

    // 校验JWT并解析
    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("opensun");
        return JWT.require(algorithm)
                .withIssuer("auth-center")
                .build()
                .verify(token); // 验证并返回解码后的JWT
    }
}
