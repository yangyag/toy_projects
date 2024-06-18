package com.yangyag.toy.web;

import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class MyControllerTest {

    @Autowired
    private PostService service;

    @Container
    //아래와같이 DB이름 설정 가능
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("postgres");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Create 동작이 정상적으로 수행 되어야 한다")
    void create() throws Exception {
        //given
        var request = PostSaveRequest.builder()
                .author("author")
                .title("title")
                .contents("contents")
                .build();

        // when
        service.create(request);

        var pageable = PageRequest.of(0,10);
        var savedData = service.getList(pageable)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("list is empty"));

        // then
        assertEquals(request.getAuthor(), savedData.getAuthor());
    }
}
