package com.tswny.init.repository;

import com.tswny.init.domain.file.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (Directory)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-18 17:03:09
 */
@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long>, QuerydslPredicateExecutor<Directory>{
}

