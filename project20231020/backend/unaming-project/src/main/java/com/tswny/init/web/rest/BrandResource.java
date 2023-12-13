package com.tswny.init.web.rest;

import com.tswny.init.domain.clothes.Brand;
import com.tswny.init.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Brand)表控制层
 *
 * @author makejava
 * @since 2023-12-05 11:01:07
 */
@RestController
@RequestMapping("/api/v1/brands")
public class BrandResource {
    /**
     * 服务对象
     */
    @Resource
    private BrandService brandService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Brand>> queryByPage(@RequestParam(required = false) String keyword,
                                                   @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.brandService.queryByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.brandService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param brand 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Brand> add(Brand brand) {
        return ResponseEntity.ok(this.brandService.insert(brand));
    }

    /**
     * 编辑数据
     *
     * @param brand 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Brand> edit(Brand brand) {
        return ResponseEntity.ok(this.brandService.update(brand));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.brandService.deleteById(id));
    }

}

