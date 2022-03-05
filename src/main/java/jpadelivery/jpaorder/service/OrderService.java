package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.*;
import jpadelivery.jpaorder.repository.MemberRepository;
import jpadelivery.jpaorder.repository.MenuRepository;
import jpadelivery.jpaorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long menuId, int count) {
        Member member = memberRepository.findOne(memberId);
        Menu menu = menuRepository.findOne(menuId);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderLine orderLine = OrderLine.createOrderLine(menu, menu.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderLine);
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancleOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancle();
    }


}
