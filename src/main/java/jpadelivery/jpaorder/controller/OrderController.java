package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.domain.Order;
import jpadelivery.jpaorder.domain.OrderLine;
import jpadelivery.jpaorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("menuId") Long menuId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, menuId, count);
        return "orders/orderComplete";
    }

    @GetMapping("/orderList")
    public String orderList(Model model, @SessionAttribute(name=SessionConstants.LOGIN_MEMBER) Member loginMember){
        if(loginMember == null) return "redirect:/";
        List<Order> orders = orderService.findOrders(loginMember.getId());
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }
}
