package com.yangyag.toy.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.QPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class PostSupportTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostSupport postSupport;

    @Test
    @DisplayName("교육중 사용한 테스트 코드")
    void name() {
        var id = 1L;

        var results = this.findById(id, "test");
    }
    @DisplayName("교육중 사용한 테스트 코드")
    private List<Post> findById(Long id, String author) {
        var jpaQuery = new JPAQueryFactory(em);
        QPost qPost = QPost.post;

        var query = jpaQuery.select(qPost)
                .from(qPost)
                .where(qPost.id.eq(id));

        if (Objects.nonNull(author)) {
            query.where(qPost.author.eq(author));
        }

        return query.fetch();
    }

    @Test
    @DisplayName("QueryDSL 을 이용하여 Post 의 목록을 가지고 올 수 있어야 한다.")
    void should_be_get_post_list() throws Exception {
        var list = postSupport.findAll();
    }
}