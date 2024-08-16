package com.tswny.init.service;

import com.querydsl.core.BooleanBuilder;
import com.tswny.init.domain.User;
import com.tswny.init.domain.enumeration.BlogOpenStateEnum;
import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.domain.homepage.QBlog;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.repository.BlogRepository;
import com.tswny.init.repository.UserRepository;
import com.tswny.init.security.UserHelper;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.service.mapper.BlogMapper;
import com.tswny.init.service.util.CommonUtil;
import com.tswny.init.util.SecurityUtils;
import com.tswny.init.web.rest.vm.BlogVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * (Blog)表服务实现类
 *
 * @author Andy
 * @since 2024-07-12 11:30:11
 */
@Transactional
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogMapper blogMapper;
    private final UserHelper userHelper;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository, BlogMapper blogMapper, UserHelper userHelper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
        this.userHelper = userHelper;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public BlogDTO queryById(Long id) {
        Optional<Blog> blogDTOOptional = this.blogRepository.findById(id);
        if (!blogDTOOptional.isPresent()) return null;
        return blogMapper.toDto(blogDTOOptional.get());
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    public Page<BlogDTO> queryBlogsByPage(String category, String keyword, BlogOpenStateEnum blogOpenState, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBlog qBlog = QBlog.blog;

        if (blogOpenState != null) {
            booleanBuilder.and(qBlog.openState.eq(blogOpenState));
        }

        if (StringUtils.isNotBlank(category)) {
            booleanBuilder.and(qBlog.category.eq(category));
        }
        if (StringUtils.isNotBlank(keyword)) {
            booleanBuilder.andAnyOf(qBlog.remark.like("%" + keyword + "%" )
                    .or(qBlog.tag.like("%" + keyword + "%" )));
        }
        return this.blogRepository.findAll(booleanBuilder, pageable).map(blogMapper::toDto);
    }


    public Page<BlogDTO> queryUserBlogsByPage(String category, String keyword, Pageable pageable) {
        User user = userHelper.getCurrentUser();

        if (user == null) throw new BadRequestException("登录用户为空");

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBlog qBlog = QBlog.blog;
        booleanBuilder.and(qBlog.user.eq(user));

        if (StringUtils.isNotBlank(category)) {
            booleanBuilder.and(qBlog.category.eq(category));
        }
        if (StringUtils.isNotBlank(keyword)) {
            booleanBuilder.andAnyOf(qBlog.remark.like("%" + keyword + "%" )
                    .or(qBlog.tag.like("%" + keyword + "%" )));
        }
        return this.blogRepository.findAll(booleanBuilder, pageable).map(blogMapper::toDto);
    }

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    public Blog insert(BlogVM blogVM) {
        blogVM.setRemark(CommonUtil.generateBlogRemark(blogVM));
        Blog blog = blogMapper.toEntity(blogVM);
        User user = userHelper.getCurrentUser();
        if (user != null) {
            blog.setUser(user);
        }
        return this.blogRepository.save(blog);
    }

    /**
     * 修改数据
     *
     * @return 实例对象
     */
    public Blog update(BlogVM blogVM) {
        if (blogVM.getId() == null) throw new BadRequestException("id 不能为空");
        Long id = blogVM.getId();
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (!blogOptional.isPresent()) throw new BadRequestException("未能查询到实体");
        blogVM.setRemark(CommonUtil.generateBlogRemark(blogVM));
        Blog blog = blogMapper.toEntity(blogVM);
        return this.blogRepository.save(blog);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.blogRepository.deleteById(id);
        return true;
    }
}
