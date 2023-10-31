package com.tswny.init.repository;

import com.tswny.init.domain.User;
import com.tswny.init.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByLogin(String login);
}
