package jpadelivery.jpaorder.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {
    @Id @GeneratedValue
    @Column(name="order_line_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    //==생성메서드==//
    public static OrderLine createOrderLine(Menu menu, int orderPrice, int count){
        OrderLine orderLine = new OrderLine();
        orderLine.setMenu(menu);
        orderLine.setOrderPrice(orderPrice);
        orderLine.setCount(count);

        return orderLine;
    }

    /**
     * 주문상품 전체 금액 조회
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
