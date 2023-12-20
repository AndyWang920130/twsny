package com.tswny.init.repository;

import com.tswny.init.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (File)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-18 17:03:10
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long>, QuerydslPredicateExecutor<File>{

}

