package ru.ndg.market.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.market.model.Order;
import ru.ndg.market.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(Integer page, Integer size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        // TODO: 30.11.2020 доделать перехват исключения
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @Transactional
    public Order saveOrUpdateOrder(Order order) {
        return orderRepository.save(order);
    }
}
