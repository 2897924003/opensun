package article.inbound.co;


import article.usecase.exception.ApiCONotPermittedException;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiCO<T> {
    private T co;
    private Long actorId;
    private boolean isPermitted;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime completeTime;

    public T getCo() {
        if (!isPermitted) {
            throw new ApiCONotPermittedException("权限不足");
        }
        return co;
    }
}



