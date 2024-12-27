package pay.inbound.ro;

/**
 * ApiRO 类是一个通用的响应对象，提供给前端所需的响应数据。
 *
 * @param <T> 响应体的类型，可以是任何类型，通常是业务对象的类型
 */
public class ApiRO<T> {

    /**
     * 响应体：封装前端需要的数据。
     */
    public T ro;

    /**
     * 响应描述：用于前端提示信息，告知用户请求的结果。
     */
    public String msg;

    /**
     * 响应状态：
     * 0 - 失败，
     * 1 - 成功，
     * 2 - 系统内部问题（目前未使用）
     */
    public Integer status;

    /**
     * 默认构造函数，初始化为空的 ApiRO 对象。
     */
    public ApiRO() {
    }

    /**
     * 私有构造函数，用于初始化包含响应体的 ApiRO 对象。
     *
     * @param ro 响应体
     */
    private ApiRO(T ro) {
        this.ro = ro;
    }

    /**
     * 创建一个待定状态的 ApiRO 对象，与Optional搭配使用。
     *
     * @param <T> 响应体的类型
     * @return 返回一个空的 ApiRO 对象
     */
    public static <T> ApiRO<T> unknown() {
        return new ApiRO<T>();
    }

    /**
     * 设置响应为成功状态，并且包含默认的成功消息。
     */
    public void beSuccess() {
        this.msg = "success";
        this.status = 1;
    }

    /**
     * 设置响应为成功状态，并且包含指定的响应体。
     *
     * @param ro 响应体，通常是业务对象
     */
    public void beSuccess(T ro) {
        this.ro = ro;
        this.msg = "success";
        this.status = 1;
    }

    /**
     * 设置响应为失败状态，并且包含默认的失败消息。
     */
    public void beFail() {
        this.msg = "fail";
        this.status = 0;
    }

    /**
     * 设置响应消息。
     *
     * @param msg 消息内容
     * @return 返回当前 ApiRO 对象，支持链式调用
     */
    public ApiRO<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 设置响应状态。
     *
     * @param status 状态值，通常是 0（失败）或 1（成功）
     * @return 返回当前 ApiRO 对象，支持链式调用
     */
    public ApiRO<T> status(int status) {
        this.status = status;
        return this;
    }

    /**
     * 返回一个失败的 ApiRO 对象，默认消息为 "failed"，状态为 0。
     *
     * @param <T> 响应体的类型
     * @return 返回一个失败的 ApiRO 对象
     */
    public static <T> ApiRO<T> fail() {
        return new ApiRO<T>().msg("failed").status(0);
    }

    /**
     * 返回一个失败的 ApiRO 对象，包含指定的响应体，默认消息为 "failed"，状态为 0。
     *
     * @param <T> 响应体的类型
     * @param ro  响应体，通常是业务对象
     * @return 返回一个包含响应体的失败 ApiRO 对象
     */
    public static <T> ApiRO<T> fail(T ro) {
        return new ApiRO<T>(ro).msg("failed").status(0);
    }

    /**
     * 返回一个成功的 ApiRO 对象，默认消息为 "success"，状态为 1。
     *
     * @param <T> 响应体的类型
     * @return 返回一个成功的 ApiRO 对象
     */
    public static <T> ApiRO<T> success() {
        return new ApiRO<T>().msg("success").status(1);
    }

    /**
     * 返回一个成功的 ApiRO 对象，包含指定的响应体，默认消息为 "success"，状态为 1。
     *
     * @param <T> 响应体的类型
     * @param ro  响应体，通常是业务对象
     * @return 返回一个包含响应体的成功 ApiRO 对象
     */
    public static <T> ApiRO<T> success(T ro) {
        return new ApiRO<T>(ro).msg("success").status(1);
    }

}
