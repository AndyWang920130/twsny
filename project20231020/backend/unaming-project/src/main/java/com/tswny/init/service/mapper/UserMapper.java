package com.tswny.init.service.mapper;

import com.tswny.init.domain.User;
import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.service.dto.BlogDTO;
import com.tswny.init.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User>{

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
