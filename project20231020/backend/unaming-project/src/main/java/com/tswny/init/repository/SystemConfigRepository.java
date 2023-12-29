package com.tswny.init.repository;

import com.tswny.init.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (SystemConfig)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-28 16:12:26
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long>, QuerydslPredicateExecutor<SystemConfig>{

}

