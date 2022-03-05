package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

}
