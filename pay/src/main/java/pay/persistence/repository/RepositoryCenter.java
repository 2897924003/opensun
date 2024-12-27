package pay.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryCenter {
    @Autowired
    public ItemRepository itemRepository;
    @Autowired
    public OrderRepository orderRepository;
}
