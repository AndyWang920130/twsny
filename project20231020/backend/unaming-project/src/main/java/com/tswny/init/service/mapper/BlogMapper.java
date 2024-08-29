package com.tswny.init.service.mapper;

import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.web.rest.vm.BlogVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomFormatter.class, UserMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog>{
    @Mapping(source = "tagList", target = "tag", qualifiedByName = "ListToString")
    Blog toEntity(BlogVM blogVM);

    @Mapping(source = "tag", target = "tagList", qualifiedByName = "StringToList")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.realName", target = "userName")
    @Mapping(source = "createdDate", target = "publishDate")
    BlogDTO toDto(Blog blog);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
