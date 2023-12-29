package com.tswny.init.web.rest;

import com.tswny.init.domain.SystemConfig;
import com.tswny.init.service.SystemConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SystemConfig)表控制层
 *
 * @author makejava
 * @since 2023-12-28 16:12:26
 */
@RestController
@RequestMapping("/api/v1/systemConfig")
public class SystemConfigResource {
    /**
     * 服务对象
     */
    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<SystemConfig>> queryByPage(@RequestParam(required = false) String keyword,
                                                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.systemConfigService.queryByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SystemConfig> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.systemConfigService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param systemConfig 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SystemConfig> add(@RequestBody SystemConfig systemConfig) {
        return ResponseEntity.ok(this.systemConfigService.insert(systemConfig));
    }

    /**
     * 编辑数据
     *
     * @param systemConfig 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SystemConfig> edit(@RequestBody SystemConfig systemConfig) {
        return ResponseEntity.ok(this.systemConfigService.update(systemConfig));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.systemConfigService.deleteById(id));
    }

}

