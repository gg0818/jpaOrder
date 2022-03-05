package jpadelivery.jpaorder.repository;

import jpadelivery.jpaorder.domain.Order;
import jpadelivery.jpaorder.domain.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(Long memberId){
        return em.createQuery("select o from Order o left join o.orderLines l" +
                        " where o.member.id =:memberId", Order.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

}
