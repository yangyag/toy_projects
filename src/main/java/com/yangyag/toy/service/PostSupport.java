package com.yangyag.toy.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangyag.toy.domain.posts.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.yangyag.toy.domain.posts.QPost.post;
import static com.yangyag.toy.domain.reply.QReply.reply;

@Repository
public class PostSupport extends QuerydslRepositorySupport {
    private EntityManager em;

    public PostSupport() {
        super(Post.class);
    }

    public List<Tuple> findAll() throws Exception {
        var jpaQuery = new JPAQueryFactory(getEntityManager());
        var query = jpaQuery
                .select(
                        post.id,
                        post.title,
                        post.contents,
                        post.author,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(count(reply.id))
                                        .from(reply)
                                , "replyCount"
                        )
                )
                .from(post)
                .orderBy(post.id.desc());

        return query.fetch();
    }
}

