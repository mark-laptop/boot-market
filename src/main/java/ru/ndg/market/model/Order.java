package ru.ndg.market.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ndg.market.bean.Cart;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "sum")
    private BigDecimal sum;

    public Order(Cart cart, User user, String address, String phone, String recipient) {
        this.user = user;
        if (address == null || address.isEmpty()) {
            this.address = user.getAddress();
        } else {
            this.address = address;
        }
        if (phone == null || phone.isEmpty()) {
            this.phone = user.getPhone();
        } else {
            this.phone = phone;
        }
        this.recipient = recipient;
        this.sum = cart.getSum();
        addOrderItems(cart.getOrderItems());
        cart.clear();
        recalculate();
    }

    private void addOrderItems(Set<OrderItem> cartOrderItems) {
        for (OrderItem cartOrderItem : cartOrderItems) {
            this.orderItems.add(cartOrderItem);
            cartOrderItem.setOrder(this);
        }
    }

    private void recalculate() {
        BigDecimal temp = new BigDecimal("0");
        for (OrderItem orderItem : this.orderItems) {
            temp = temp.add(orderItem.getQuantity());
        }
        this.quantity = temp;
    }
}
