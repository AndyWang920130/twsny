package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.Menu;
import com.tswny.init.domain.QMenu;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.repository.MenuRepository;
import com.tswny.init.web.rest.vm.MenuVM;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;
import java.util.Optional;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-29 16:08:16
 */
@Transactional
@Service("menuService")
public class MenuService {
    @Resource
    private MenuRepository menuRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Menu queryById(Long id) {
        return this.menuRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     * @return 查询结果
     */
    public Page<Menu> queryByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = generateBooleanBuilder(keyword);
        return this.menuRepository.findAll(booleanBuilder, pageable);
    }

    public Page<Menu> queryRootByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = generateBooleanBuilder(keyword);
        QMenu qMenu = QMenu.menu;
        booleanBuilder.and(qMenu.parent.isNull());
        return this.menuRepository.findAll(booleanBuilder, pageable);
    }

    public Page<Menu> queryByParent(Long parentId, String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = generateBooleanBuilder(keyword);
        QMenu qMenu = QMenu.menu;
        booleanBuilder.and(qMenu.parent.id.eq(parentId));
        return this.menuRepository.findAll(booleanBuilder, pageable);
    }

    private BooleanBuilder generateBooleanBuilder(String keyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QMenu qMenu = QMenu.menu;

        booleanBuilder.andAnyOf(qMenu.disable.ne(true),
                qMenu.disable.isNull());

        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.and(qMenu.name.like("%" + keyword + "%"));
        }
        return booleanBuilder;
    }

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    public Menu insert(MenuVM menuVM) {
        Menu menu = new Menu();
        menu.setName(menuVM.getName());
        menu.setUrl(menuVM.getUrl());
        Long parentId = menuVM.getParentId();

        if (parentId != null) {
            Optional<Menu> menuOptional = menuRepository.findById(parentId);
            if (!menuOptional.isPresent()) throw new BadRequestException("parent id is not exist");
            menu.setParent(menuOptional.get());
        }

        return this.menuRepository.save(menu);
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    public Menu update(Menu menu) {
        return this.menuRepository.save(menu);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.menuRepository.deleteById(id);
        return true;
    }
}
