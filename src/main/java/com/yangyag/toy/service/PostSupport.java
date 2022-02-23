package com.yangyag.toy.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.web.dto.post.PostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.yangyag.toy.domain.posts.QPost.post;
import static com.yangyag.toy.domain.reply.QReply.reply;

@Repository
public class PostSupport extends AbstractQueryDslFactory {
    public PostSupport() {
        super(Post.class);
    }

    public List<PostRequest> findAll() throws Exception {
        var query = this.searchQuery(null);

        query.from(post);

        return query.fetch();
    }

    public Page<PostRequest> findAllBy(PostRequest request, Pageable pageable) {
        var title = request.getTitle();
        var query = this.searchQuery(title);

        Objects
                .requireNonNull(getQuerydsl())
                .applyPagination(
                        pageable,
                        query
                );


        return new PageImpl<>(query.fetch(), pageable, this.countBy(title));
    }

    public long countBy(String title) {
        var count = this.queryFactory()
                .select(post.id.countDistinct())
                .from(post);

        if(Objects.nonNull(title)) {
            count.where(post.title.contains(title));
        }

        return count.fetchFirst();
    }

    public JPAQuery<PostRequest> searchQuery(String title)  {
        var factory = this.queryFactory();
        var query = factory
                .select(
                        Projections.fields(
                                PostRequest.class,
                                post.id,
                                post.title,
                                post.contents,
                                post.author,
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(count(reply.id))
                                                .from(reply)
                                                .where(reply.post.id.eq(post.id))
                                        , "replyCount"
                                )
                        )
                )
                .from(post);

        if(Objects.nonNull(title)) {
            query.where(post.title.contains(title));
        }

        return query;
    }
}

