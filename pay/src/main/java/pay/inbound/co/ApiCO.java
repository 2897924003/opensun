package pay.inbound.co;


/**
 * 统一请求模板
 *
 * @param <T>
 */
public class ApiCO<T> {
    public T co;//命令对象
    public Long actor;//发起者身份标识-需要验证

}
