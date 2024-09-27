package com.tswny.init.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tswny.init.domain.QUser;
import com.tswny.init.domain.User;
import com.tswny.init.domain.enumeration.BlogCollectStateEnum;
import com.tswny.init.domain.enumeration.BlogLikeStateEnum;
import com.tswny.init.domain.enumeration.BlogOpenStateEnum;
import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.domain.homepage.QBlog;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.handler.exception.UnAuthorizedException;
import com.tswny.init.repository.BlogRepository;
import com.tswny.init.repository.UserRepository;
import com.tswny.init.security.UserHelper;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.service.mapper.BlogMapper;
import com.tswny.init.service.util.CommonUtil;
import com.tswny.init.web.rest.vm.BlogCollectVM;
import com.tswny.init.web.rest.vm.BlogLikeVM;
import com.tswny.init.web.rest.vm.BlogVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final JPAQueryFactory jpaQueryFactory;


    public BlogService(BlogRepository blogRepository, UserRepository userRepository, BlogMapper blogMapper, UserHelper userHelper, JPAQueryFactory jpaQueryFactory) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogMapper = blogMapper;
        this.userHelper = userHelper;
        this.jpaQueryFactory = jpaQueryFactory;
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
    public Page<BlogDTO> queryBlogsByPage(String userName, String category, String keyword, BlogOpenStateEnum blogOpenState, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBlog qBlog = QBlog.blog;

        if (StringUtils.isNotBlank(userName)) {
            booleanBuilder.and(qBlog.user.login.eq(userName));
        }

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

        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "createdDate");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "title");
        Sort sort = Sort.by(order1, order2);

        List<Blog> blogList1 = jpaQueryFactory
                .selectFrom(qBlog)
                .where(booleanBuilder)
                .orderBy(qBlog.createdBy.desc(), qBlog.title.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Blog> blogList2 = jpaQueryFactory
                .selectFrom(qBlog)
                .where(booleanBuilder)
                .orderBy(qBlog.id.multiply(0.3)
                        .add(qBlog.user.id.multiply(0.7))
                        .desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        QUser qUser = QBlog.blog.user;
        List<Blog> blogList3 =jpaQueryFactory
                .selectFrom(qBlog)
                .where(booleanBuilder)
                .leftJoin(qBlog.usersLiked)
                .groupBy(qBlog.id)
                .orderBy(qUser.count().desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Pageable recommendPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return this.blogRepository.findAll(booleanBuilder, recommendPageable).map(blogMapper::toDto);
    }


    public Page<BlogDTO> queryUserBlogsByPage(String category, BlogOpenStateEnum openState, String keyword, Pageable pageable) {
        User user = userHelper.getCurrentUser();

        if (user == null) throw new BadRequestException("登录用户为空");

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBlog qBlog = QBlog.blog;
        booleanBuilder.and(qBlog.user.eq(user));

        if (StringUtils.isNotBlank(category)) {
            booleanBuilder.and(qBlog.category.eq(category));
        }
        if(openState != null) {
            booleanBuilder.and(qBlog.openState.eq(openState));
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
        if (user == null) throw new UnAuthorizedException();
        // if (user == null) throw new UnAuthorizedException1();
        // if (user == null) throw new BadRequestException("unAuthorized");


        blog.setUser(user);
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
//        Blog blog = blogOptional.get();
//        blogMapper.partialUpdate(blog, blogVM);
        Blog blog = blogMapper.toEntity(blogVM);
        User user = userHelper.getCurrentUser();
        if (user == null) throw new UnAuthorizedException();
        blog.setUser(user);
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
        User user = userHelper.getCurrentUser();
        if (user == null) throw new UnAuthorizedException();
        return true;
    }

    public void changeLikeState(BlogLikeVM blogLikeVM) {
        Long blogId = blogLikeVM.getId();
        if (blogId == null) throw new BadRequestException("id 不能为空");
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if (!blogOptional.isPresent()) throw new BadRequestException("blog不存在");
        Blog blog = blogOptional.get();
        User user = userHelper.getCurrentUser();
        BlogLikeStateEnum blogLikeStateEnum = blogLikeVM.getLikeState();
        switch (blogLikeStateEnum) {
            case like:
                blog.getUsersLiked().add(user);
                break;
            case unlike:
                blog.getUsersLiked().remove(user);
                break;
        }
        blogRepository.save(blog);
    }

    public void changeCollectState(BlogCollectVM blogCollectVM) {
        Long blogId = blogCollectVM.getId();
        if (blogId == null) throw new BadRequestException("id 不能为空");
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if (!blogOptional.isPresent()) throw new BadRequestException("blog不存在");
        Blog blog = blogOptional.get();
        User user = userHelper.getCurrentUser();

        BlogCollectStateEnum blogCollectStateEnum = blogCollectVM.getCollectState();
        switch (blogCollectStateEnum) {
            case collect:
                blog.getUsersCollected().add(user);
                break;
            case unCollect:
                blog.getUsersCollected().remove(user);
                break;
        }

        blogRepository.save(blog);
    }

    public boolean isLiked(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if (!blogOptional.isPresent()) throw new BadRequestException("blog不存在");
        Blog blog = blogOptional.get();
        User user = userHelper.getCurrentUser();
        return blog.getUsersLiked().contains(user);
    }

    public boolean isCollected(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if (!blogOptional.isPresent()) throw new BadRequestException("blog不存在");
        Blog blog = blogOptional.get();
        User user = userHelper.getCurrentUser();
        return blog.getUsersCollected().contains(user);
    }
}
