package jpadelivery.jpaorder.controller;

import jpadelivery.jpaorder.domain.Menu;
import jpadelivery.jpaorder.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/menu")
    public String list(Model model) {
        List<Menu> menus = menuService.findMenu();
        model.addAttribute("menus", menus);
        return "menus/menulist";
    }
}
