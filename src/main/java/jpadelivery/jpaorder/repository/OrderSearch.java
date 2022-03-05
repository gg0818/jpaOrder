package jpadelivery.jpaorder.repository;

import jpadelivery.jpaorder.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberEmail;
    private OrderStatus orderStatus;
}
