package article.usecase.exception;

import java.util.function.Supplier;

/**
 * 业务异常基类-所有业务异常需要继承此类：【参数问题，网络问题...】
 */
public abstract class BusinessException extends RuntimeException implements Supplier<RuntimeException> {

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(String msg) {
        super(msg);
    }

    /**
     * 获取异常类
     *
     * @return 对应业务异常类
     */
    @Override
    public RuntimeException get() {
        return this;
    }

}
