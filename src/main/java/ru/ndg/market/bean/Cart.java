package ru.ndg.market.bean;

import ru.ndg.market.model.OrderItem;
import ru.ndg.market.model.Product;

import java.math.BigDecimal;
import java.util.Set;

public interface Cart {

    void addProduct(Product product);
    void removeProductById(Long id);
    void recalculate();
    Set<OrderItem> getOrderItems();
    BigDecimal getSum();
    void clear();
}
