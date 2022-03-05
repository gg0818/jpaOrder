package jpadelivery.jpaorder.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //ORDER ,CANCEL

    //== 연관관계 편의 메서드 ==//
    public void addOrderLine(OrderLine orderLine){
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성자 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderLine... orderLines) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderLine orderLine : orderLines){
            order.addOrderLine(orderLine);
        }
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancle() {
        if (delivery.getStatus() == DeliveryStatus.READY ) {// 배송시작
            throw new IllegalStateException("이미 배송시작된 상품은 취소가 불가능 합니다.");
        }
        if (delivery.getStatus() == DeliveryStatus.COMP ) {// 배송완료
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
    }

    //== 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderLine orderLine : orderLines) {
            totalPrice += orderLine.getTotalPrice();
        }
        return totalPrice;
    }

}
