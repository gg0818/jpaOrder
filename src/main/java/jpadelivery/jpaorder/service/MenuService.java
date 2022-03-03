package jpadelivery.jpaorder.service;

import jpadelivery.jpaorder.domain.Menu;
import jpadelivery.jpaorder.domain.Store;
import jpadelivery.jpaorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public void saveMenu(Menu menu){
        menuRepository.save(menu);
    }

    @Transactional
    public Menu updateMenu(Long menuId, String name, int price, String info){
        Menu findMenu = menuRepository.findOne(menuId);
        findMenu.setName(name);
        findMenu.setPrice(price);
        findMenu.setInfo(info);
        return findMenu;
    }

    public List<Menu> findMenu(){
        return menuRepository.findAll();
    }

    public Menu findOne(Long menuId){
        return menuRepository.findOne(menuId);
    }

}
