package com.yangyag.toy.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangyag.toy.domain.posts.Post;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public abstract class AbstractQueryDslFactory extends QuerydslRepositorySupport {
    public AbstractQueryDslFactory(Class<?> domainClass) {
        super(domainClass);
    }
    
    protected JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(getEntityManager());
    }
}
