package article.atcl.auth.service;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class AuthorizeUtils {

    /**
     * 校验jwt中用户id，与资源id是否相同
     *
     * @param targetId 请求操作的资源对应用户id
     */
    public static void commonVerify(long targetId) {
        Long authContext = (Long) RequestContextHolder.currentRequestAttributes().getAttribute("authContext", RequestAttributes.SCOPE_REQUEST);
        if (authContext == null || authContext != targetId) {
            throw new RuntimeException("你正在进行违法操作");
        }
    }
}
