package pay.inbound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pay.inbound.co.ApiCO;
import pay.inbound.ro.ApiRO;
import pay.order.domain.Cart;
import pay.order.domain.Order;
import pay.usecase.PayService;


@RestController
public class PayUseCaseController {

    @Autowired
    private PayService payService;

    @PostMapping("/test")
    public ApiRO<Order> commitCart(@RequestBody ApiCO<Cart> apiCO) {
        return payService.commitCart(apiCO);
    }
}
