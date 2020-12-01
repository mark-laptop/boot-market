package ru.ndg.market.service.order;

import org.springframework.data.domain.Page;
import ru.ndg.market.model.Order;

public interface OrderService {

    Page<Order> getAllOrders(Integer page, Integer size);
    Order getOrderById(Long id);
    Order saveOrUpdateOrder(Order order);
}
