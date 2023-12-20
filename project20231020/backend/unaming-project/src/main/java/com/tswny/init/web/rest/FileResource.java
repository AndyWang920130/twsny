package com.tswny.init.web.rest;

import com.tswny.init.domain.file.File;
import com.tswny.init.service.FileService;
import com.tswny.init.web.rest.vm.FileVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (File)表控制层
 *
 * @author makejava
 * @since 2023-12-18 17:03:09
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileResource {
    /**
     * 服务对象
     */
    @Resource
    private FileService fileService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<File>> queryByPage(@RequestParam(required = false) String keyword,
                                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.fileService.queryByPage(keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<File> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.fileService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<File> add(@RequestBody FileVM fileVM) {
        return ResponseEntity.ok(this.fileService.insert(fileVM));
    }

    /**
     * 编辑数据
     *
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<File> edit(@RequestBody FileVM fileVM) {
        return ResponseEntity.ok(this.fileService.update(fileVM));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.fileService.deleteById(id));
    }

}

