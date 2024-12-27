package pay.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pay.persistence.po.ItemPO;


public interface ItemRepository extends JpaRepository<ItemPO, String> {

}
