package article.inbound.ro;

/**
 * @param <T>
 */
public class ApiRO<T> {
    /**
     * 响应对象:前端所需对象
     */
    public T ro;
    /**
     * 响应描述：前端用户提示词
     */
    public String msg;
    /**
     * 响应状态:0-用户导致失败，1-成功，2-系统内部问题
     */
    public Integer status;

    public ApiRO() {
    }

    private ApiRO(T ro) {
        this.ro = ro;
    }

    public ApiRO<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public ApiRO<T> status(int status) {
        this.status = status;
        return this;
    }

    /**
     * 无响应对象，有响应消息的失败响应
     *
     * @param msg 响应消息
     */
    public static ApiRO<Void> fail(String msg) {
        return new ApiRO<Void>().msg(msg).status(0);
    }

    /**
     * 有响应对象，默认响应消息的失败响应
     *
     * @param ro 响应消息
     */
    public static <T> ApiRO<T> fail(T ro) {
        return new ApiRO<T>(ro).msg("failed").status(0);
    }

    /**
     * 无响应对象，有响应消息的成功响应
     *
     * @param msg 响应消息
     */
    public static ApiRO<Void> success(String msg) {
        return new ApiRO<Void>().msg(msg).status(1);
    }

    /**
     * 无响应对象，默认响应消息的成功响应
     */
    public static ApiRO<Void> success() {
        return new ApiRO<Void>().msg("success").status(1);
    }

    /**
     * 有响应对象，默认响应消息的成功响应
     *
     * @param ro 响应消息
     */
    public static <T> ApiRO<T> success(T ro) {
        return new ApiRO<T>(ro).msg("success").status(1);
    }

    public static <T> ApiRO<T> success(T ro, String msg) {
        return new ApiRO<T>(ro).msg(msg).status(1);
    }

}
