package com.tswny.init.service.mapper;

import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.web.rest.vm.BlogVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomFormatter.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog>{
    @Mapping(source = "tagList", target = "tag", qualifiedByName = "ListToString")
    Blog toEntity(BlogVM blogVM);

    @Mapping(source = "tag", target = "tagList", qualifiedByName = "StringToList")
    BlogDTO toDto(Blog blog);
}
