package com.tswny.init.web.rest;

import com.tswny.init.domain.clothes.Clothes;
import com.tswny.init.service.ClothesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Clothes)表控制层
 *
 * @author makejava
 * @since 2023-12-05 11:40:21
 */
@RestController
@RequestMapping("/api/v1/clothes")
public class ClothesResource {
    /**
     * 服务对象
     */
    @Resource
    private ClothesService clothesService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Clothes>> queryByPage(@RequestParam(required = false) String keyword,
                                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.clothesService.queryByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Clothes> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.clothesService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param clothes 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Clothes> add(Clothes clothes) {
        return ResponseEntity.ok(this.clothesService.insert(clothes));
    }

    /**
     * 编辑数据
     *
     * @param clothes 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Clothes> edit(Clothes clothes) {
        return ResponseEntity.ok(this.clothesService.update(clothes));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.clothesService.deleteById(id));
    }

}

