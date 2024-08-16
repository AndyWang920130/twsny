package com.tswny.init.service.mapper;

import com.tswny.init.domain.User;
import com.tswny.init.domain.homepage.Blog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
