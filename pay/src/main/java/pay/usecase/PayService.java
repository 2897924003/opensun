package pay.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pay.inbound.co.ApiCO;
import pay.inbound.ro.ApiRO;
import pay.order.domain.Cart;
import pay.order.domain.Order;
import pay.persistence.po.ItemPO;
import pay.persistence.repository.RepositoryCenter;

import java.math.BigDecimal;
import java.util.List;


@Service
public class PayService {

    @Autowired
    private RepositoryCenter repositoryCenter;

    public ApiRO<Order> commitCart(ApiCO<Cart> apiCO) {
        Order order = Order.create();
        List<String> items = apiCO.co.items().keySet().stream().toList();
        List<ItemPO> allById = repositoryCenter.itemRepository.findAllById(items);
        BigDecimal zero = BigDecimal.ZERO;
        for (ItemPO item : allById) {
            zero = zero.add(item.getPrice().multiply(BigDecimal.valueOf(apiCO.co.items().get(item.getId()))));
        }
        order.setAmount(zero.doubleValue());
        return ApiRO.success(order);
    }
}
