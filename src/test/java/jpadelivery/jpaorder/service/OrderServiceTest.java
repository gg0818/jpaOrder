package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.*;
import jpadelivery.jpaorder.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Menu menu = createMenu("김치찌개",6000,"매워여");

        //when
        Long orderId = orderService.order(member.getId(), menu.getId(), 3);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, getOrder.getOrderLines().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 6000 * 3, getOrder.getTotalPrice());
        //assertEquals("매장 상태가 OPEN이여야 한다. ");
    }


    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Menu menu = createMenu("된장찌개",6000,"차돌된장찌개");
        int orderCount =2;

        Long orderId = orderService.order(member.getId(), menu.getId(), orderCount);

        //when
        orderService.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCLE이다.", OrderStatus.CANCEL, getOrder.getStatus());
    }

    private Member createMember() {
        Member member = new Member();
        member.setEmail("gihyun0818@gmail.com");
        member.setAddress(new Address("서울", "양천구", "883028"));
        em.persist(member);
        return member;
    }

    private Menu createMenu(String name, int price, String info){
        Store store = new Store();
        store.setName("서울맛집");
        em.persist(store);

        Store findStore = em.find(Store.class, store.getId());

        Menu menu = new Menu();
        menu.setStore(findStore);
        menu.setName(name);
        menu.setPrice(price);
        menu.setInfo(info);
        em.persist(menu);

        return menu;
    }
}