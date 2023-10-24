package com.tswny.init.repository;

import com.tswny.init.domain.Person;
import com.tswny.init.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long>, QuerydslPredicateExecutor<Website> {
}
