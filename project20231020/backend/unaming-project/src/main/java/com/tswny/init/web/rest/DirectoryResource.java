package com.tswny.init.web.rest;

import com.tswny.init.domain.file.Directory;
import com.tswny.init.service.DirectoryService;
import com.tswny.init.web.rest.vm.DirectorVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Directory)表控制层
 *
 * @author makejava
 * @since 2023-12-18 17:03:09
 */
@RestController
@RequestMapping("/api/v1/directory")
public class DirectoryResource {
    /**
     * 服务对象
     */
    @Resource
    private DirectoryService directoryService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Directory>> queryByPage(@RequestParam(required = false) String keyword,
                                                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.directoryService.queryByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Directory> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.directoryService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Directory> add(@RequestBody DirectorVM directorVM) {
        return ResponseEntity.ok(this.directoryService.insert(directorVM));
    }

    /**
     * 编辑数据
     *
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Directory> edit(@RequestBody DirectorVM directorVM) {
        return ResponseEntity.ok(this.directoryService.update(directorVM));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.directoryService.deleteById(id));
    }

}

