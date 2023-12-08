package com.tswny.init.repository;

import com.tswny.init.domain.clothes.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * (Clothes)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-05 11:40:21
 */
@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long>, QuerydslPredicateExecutor<Clothes>{

}

