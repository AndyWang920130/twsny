package com.tswny.init.repository;

import com.tswny.init.domain.homepage.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (Blog)表数据库访问层
 *
 * @author Andy
 * @since 2024-07-12 11:30:05
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, QuerydslPredicateExecutor<Blog>{

}

