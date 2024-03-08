package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PostServiceTest {

    @InjectMocks
    private PostService service;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Delete가 정상적으로 수행되어야 한다.")
    void delete() throws Exception {
        // given
        var post = mock(Post.class);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        service.delete(anyLong());

        // then
        then(postRepository).should(atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("아이디가 없을 때 IllegalException 을 발생시켜야 한다.")
    void shouldBeThrowIllegalException_WhenIdIsEmpty() {
        // given
        var post = mock(Post.class);

        given(postRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(10L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Create 동작이 정상적으로 수행 되어야 한다")
    void create() throws Exception {
        // given
        var request = mock(PostSaveRequest.class);

        // when
        service.create(request);

        // then
        then(postRepository).should(atLeastOnce()).save(any(Post.class));
    }

    @Test
    @DisplayName("Update 동작이 정상적으로 수행 되어야 한다")
    void update() throws Exception {
        var post = mock(Post.class);
        var request = mock(PostUpdateRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        service.update(anyLong(), request);

        // then
        then(postRepository).should(atLeastOnce()).save(any(Post.class));
    }

    @Test
    @DisplayName("Update 수행시 ID 가 없을때 IllegalException 이 발생해야 한다")
    void should_be_throws_illegalException_when_update_by_id_is_empty() throws Exception {
        var postUpdateRequest = mock(PostUpdateRequest.class);
        var id = 1L;

        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, postUpdateRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Post 의 목록을 가지고 올 수 있어야 한다")
    void getList() throws Exception {
        //given
        var pageable = mock(Pageable.class);

        //when
        var list = service.getList(pageable);

        //then
        then(postRepository).should(atLeastOnce()).findAll(pageable);
    }


     @Test
    void streamMapTest() {
        List<String> names = Arrays.asList("Steve", "Peter", "Kim", "Jane", "Lee");

        names.stream()
                .filter(name -> name.startsWith("S"))
                .map(String::toUpperCase).forEach(System.out::println);

    }

    @Test
    void streamParallelTest() {
        // 1부터 100까지의 숫자 리스트 생성
        List<Integer> numbers = IntStream.rangeClosed(1, 100)
                .boxed()
                .collect(Collectors.toList());

        // 순차 스트림 처리
        long startTimeSequential = System.nanoTime();
        List<Double> resultSequential = numbers.stream()
                .map(number -> process(number))
                .collect(Collectors.toList());
        long endTimeSequential = System.nanoTime();

        // 병렬 스트림 처리
        long startTimeParallel = System.nanoTime();
        List<Double> resultParallel = numbers.parallelStream()
                .map(number -> process(number))
                .collect(Collectors.toList());
        long endTimeParallel = System.nanoTime();

        System.out.println("순차 스트림 처리 시간: " + (endTimeSequential - startTimeSequential) + "ns");
        System.out.println("병렬 스트림 처리 시간: " + (endTimeParallel - startTimeParallel) + "ns");

        // 결과 출력 (처리 결과 확인)
        System.out.println(resultSequential.subList(0, 5));
        System.out.println(resultParallel.subList(0, 5));

    }

    public static double process(int number) {
        return number / 2.0;
    }

    @Test
    void lambdaTest() {
        List<String> list = Arrays.asList("Java", "Python", "JavaScript", "C++");

        // Predicate를 사용하여 문자열 길이가 4 이상인 요소만 필터링
        Predicate<String> longerThanThree = s -> s.length() > 3;

        // 스트림 API와 람다 표현식을 사용하여 필터링 및 출력
        list.stream()
                .filter(longerThanThree)
                .forEach(System.out::println);

        // 스트림 API와 람다 표현식을 사용하여 각 문자열을 대문자로 변환 후 출력
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    void predicateTest() {
        List<String> names = Arrays.asList("John", "Smith", "Samuel", "Catley", "Sie");
        Predicate<String> lengthMoreThanFour = str -> str.length() > 4;

        List<String> filteredNames = names.stream()
                .filter(lengthMoreThanFour) // 여기서 Predicate의 test 메서드가 내부적으로 호출됩니다.
                .collect(Collectors.toList());

    }


    @Test
    void functionalInterfaceTest() {
        Function<String, Integer> stringLengthFunction = s -> s.length();

        Function<String, String> stringToUpperCase = s -> s.toUpperCase();

        var rst = stringLengthFunction.apply("aaaaa");
        var rst2 = stringToUpperCase.apply("bbbbb");

        System.out.println(rst);
        System.out.println(rst2);
    }

    @Test
    void optionalTest() {
        Optional<String> a = Optional.of("Test");
        Optional<String> b = Optional.ofNullable(null);
        String sss = null;
        String c = Optional.ofNullable(sss).orElse("Test3");

        if(a.isPresent()) {
            System.out.println(a.get());
        }

        if(b.isPresent()) {
            System.out.println(b.get());
        }

        System.out.println(c);
    }

    @Test
    void optionTest2() {
        Optional<String> a = Optional.of("Test");
        Optional<String> b = Optional.ofNullable(null);
        String sss = null;
        String c = Optional.ofNullable(sss).orElse("Test3");

        if(a.isPresent()) {
            System.out.println(a.get());
        }

        if(b.isPresent()) {
            System.out.println(b.get());
        }

        // c가 Optional 객체가 아니므로, isPresent() 메서드를 사용할 수 없습니다.
        // 대신 c는 직접적으로 null 검사 없이 사용될 수 있습니다.
        System.out.println(c);

    }
}
