package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Address;
import jpadelivery.jpaorder.domain.Member;
import jpadelivery.jpaorder.domain.Menu;
import jpadelivery.jpaorder.service.MemberService;
import jpadelivery.jpaorder.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MenuController {
    private final MenuService menuService;
    private final MemberService memberService;

    @GetMapping("/menu")
    public String list(Model model) {
        List<Menu> menus = menuService.findMenu();
        model.addAttribute("menus", menus);
        model.addAttribute("menuForm", new MenuForm());
        return "menus/menulist";
    }

}
