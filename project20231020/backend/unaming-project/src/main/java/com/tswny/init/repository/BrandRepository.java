package com.tswny.init.repository;

import com.tswny.init.domain.clothes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (Brand)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-05 11:01:07
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, QuerydslPredicateExecutor<Brand>{

}

