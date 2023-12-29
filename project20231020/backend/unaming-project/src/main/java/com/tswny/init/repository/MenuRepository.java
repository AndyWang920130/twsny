package com.tswny.init.repository;

import com.tswny.init.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-29 16:08:16
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, QuerydslPredicateExecutor<Menu>{

}

