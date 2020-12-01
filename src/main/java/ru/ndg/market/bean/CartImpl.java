package ru.ndg.market.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.ndg.market.model.OrderItem;
import ru.ndg.market.model.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
@Getter
@Setter
@NoArgsConstructor
public class CartImpl implements Cart {

    private Set<OrderItem> orderItems;
    private BigDecimal sum;

    @PostConstruct
    public void init() {
        this.orderItems = new HashSet<>();
        this.sum = new BigDecimal("0");
    }

    @Override
    public void addProduct(Product product) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().equals(product)) {
                orderItem.setQuantity(orderItem.getQuantity().add(new BigDecimal("1")));
                orderItem.recalculate(orderItem.getPrice(), orderItem.getQuantity());
                recalculate();
                return;
            }
        }
        orderItems.add(new OrderItem(product));
        recalculate();
    }

    @Override
    public void removeProductById(Long id) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().equals(id)) {
                orderItems.remove(orderItem);
                recalculate();
                return;
            }
        }
    }

    @Override
    public void recalculate() {
        BigDecimal temp = new BigDecimal("0");
        for (OrderItem orderItem : orderItems) {
            temp = temp.add(orderItem.getSum());
        }
        this.sum = temp;
    }

    @Override
    public void clear() {
        orderItems.clear();
    }
}
