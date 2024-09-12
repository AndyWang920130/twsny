package com.tswny.init.web.rest;

import com.tswny.init.domain.enumeration.BlogOpenStateEnum;
import com.tswny.init.service.BlogService;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.web.rest.vm.BlogCollectVM;
import com.tswny.init.web.rest.vm.BlogLikeVM;
import com.tswny.init.web.rest.vm.BlogVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (Blog)表控制层
 *
 * @author Andy
 * @since 2024-07-12 11:32:53
 */
@RestController
@RequestMapping("/api/v1/blogs")
public class BlogResource {
    /**
     * 服务对象
     */
    @Resource
    private BlogService blogService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<BlogDTO>> queryByPage(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String keyword,
                                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.blogService.queryBlogsByPage(null, category, keyword, BlogOpenStateEnum.PUBLIC, pageable));
    }

    @GetMapping("public")
    public ResponseEntity<Page<BlogDTO>> queryPublicBlogsByPage(@RequestParam(required = false) String category,
                                                                @RequestParam(required = false) String userName,
                                                                @RequestParam(required = false) String keyword,
                                                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.blogService.queryBlogsByPage(userName, category, keyword, BlogOpenStateEnum.PUBLIC, pageable));
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("personal")
    public ResponseEntity<Page<BlogDTO>> queryUserBlogsByPage(@RequestParam(required = false) String category,
                                                              @RequestParam(required = false) BlogOpenStateEnum openState,
                                                              @RequestParam(required = false) String keyword,
                                                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.blogService.queryUserBlogsByPage(category, openState, keyword, pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<BlogDTO> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.blogService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid BlogVM blogVM) {
        this.blogService.insert(blogVM);
        return ResponseEntity.ok().build();
    }

    /**
     * 编辑数据
     *
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Void> edit(@RequestBody @Valid BlogVM blogVM) {
        this.blogService.update(blogVM);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.blogService.deleteById(id));
    }

    @PutMapping("changeLikeState")
    public ResponseEntity<Void> changeLikeState(@RequestBody @Valid BlogLikeVM blogLikeVM) {
        this.blogService.changeLikeState(blogLikeVM);
        return ResponseEntity.ok().build();
    }

    @PutMapping("changeCollectState")
    public ResponseEntity<Void> changeCollectState(@RequestBody @Valid BlogCollectVM blogCollectVM) {
        this.blogService.changeCollectState(blogCollectVM);
        return ResponseEntity.ok().build();
    }


    @GetMapping("isLiked/{blogId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long blogId) {
        boolean isLiked = this.blogService.isLiked(blogId);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("isCollected/{blogId}")
    public ResponseEntity<Boolean> isCollected(@PathVariable Long blogId) {
        boolean isCollected = this.blogService.isCollected(blogId);
        return ResponseEntity.ok(isCollected);
    }
}

