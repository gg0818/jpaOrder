package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Address;
import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.domain.Menu;
import jpadelivery.jpaorder.domain.Order;
import jpadelivery.jpaorder.service.MemberService;
import jpadelivery.jpaorder.service.MenuService;
import jpadelivery.jpaorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final MenuService menuService;
    private final MemberService memberService;

    @PostMapping("/orderCart")
    public String cartView(@Valid MenuForm form,
                           @SessionAttribute(name=SessionConstants.LOGIN_MEMBER) Member loginMember,
                           Model model){
        Menu menu = menuService.findOne(form.getId());
        if(loginMember == null){
            return "home";
        }
        // 세션에서 아이디 가져옴
        Member member = memberService.findOne(loginMember.getId());
        Address memberAdress = member.getAddress();
        model.addAttribute("menu", menu);
        model.addAttribute("menuform", form);
        model.addAttribute("member", member);
        model.addAttribute("memberAdress", memberAdress);
        return "orders/cart";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("menuId") Long menuId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, menuId, count);
        return "orders/orderComplete";
    }

    @GetMapping("/orderList")
    public String orderList(Model model, @SessionAttribute(name=SessionConstants.LOGIN_MEMBER) Member loginMember){
        if(loginMember == null) return "logins/loginForm";
        List<Order> orders = orderService.findOrders(loginMember.getId());
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

}
