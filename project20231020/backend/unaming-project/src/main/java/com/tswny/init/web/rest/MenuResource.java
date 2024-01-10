package com.tswny.init.web.rest;

import com.tswny.init.domain.Menu;
import com.tswny.init.service.MenuService;
import com.tswny.init.web.rest.vm.MenuVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2023-12-29 16:08:16
 */
@RestController
@RequestMapping("/api/v1/menu")
public class MenuResource {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Menu>> queryByPage(@RequestParam(required = false) String keyword,
                                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.menuService.queryByPage(keyword, pageable));
    }


    @GetMapping("root")
    public ResponseEntity<Page<Menu>> queryRootByPage(@RequestParam(required = false) String keyword,
                                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.menuService.queryRootByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Menu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.menuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param menu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Menu> add(@RequestBody MenuVM menu) {
        return ResponseEntity.ok(this.menuService.insert(menu));
    }

    /**
     * 编辑数据
     *
     * @param menu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Menu> edit(@RequestBody Menu menu) {
        return ResponseEntity.ok(this.menuService.update(menu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.menuService.deleteById(id));
    }

}

