package ru.ndg.market.service.order;

import org.springframework.data.domain.Page;
import ru.ndg.market.model.Order;
import ru.ndg.market.model.User;

public interface OrderService {

    Page<Order> getAllOrders(Integer page, Integer size);
    Page<Order> getAllOrdersByUser(User user, Integer page);
    Order getOrderById(Long id);
    Order saveOrUpdateOrder(Order order);
}
