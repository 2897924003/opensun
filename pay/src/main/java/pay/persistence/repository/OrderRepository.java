package pay.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pay.persistence.po.OrderPO;

public interface OrderRepository extends JpaRepository<OrderPO, String> {
}
