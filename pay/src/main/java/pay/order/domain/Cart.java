package pay.order.domain;

import java.util.Map;

/**
 * 购物车-商品id，商品数目
 */
public record Cart(
        Map<String, Integer> items
) {

}
