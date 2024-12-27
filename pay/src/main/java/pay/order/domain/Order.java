package pay.order.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单聚合根:
 */
@Data
public class Order {

    //用户号
    private String userId;
    //订单号-自生成
    private String orderId;
    //创建时间-自生成
    private LocalDateTime createdTime;
    //完成时间
    private LocalDateTime completedTime;
    //订单状态
    private Status status;
    //订单金额
    private Double amount;


    //生成订单号
    public String generateOrderId() {
        return "%s:%s".formatted(UUID.randomUUID().toString(), userId);
    }


    public static Order create() {
        Order order = new Order();
        return order;
    }


    enum Status {
        Pending,
        Paid,
        Cancelled,
        Completed
    }


}
