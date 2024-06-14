package com.yangyag.toy.service;

import com.yangyag.toy.service.impl.ChatGPTService;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final PostService postService;
    private final ChatGPTService chatGPTService;

    @Value("${spring.scheduling.enabled}")
    private boolean schedulingEnabled;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Scheduled(fixedDelayString = "#{T(java.util.concurrent.ThreadLocalRandom).current().nextInt(1 * 60 * 60 * 1000, 2 * 60 * 60 * 1000)}")
    public void autoWrite() {

        logger.info("schedulingEnabled ==> " + schedulingEnabled);

        if(schedulingEnabled) {
            var contents = this.getContentsByGpt();

            var postSaveRequest = PostSaveRequest.builder()
                    .title("GPT가 쓰는 자유 주제")
                    .author("Chat GPT")
                    .contents(contents)
                    .build();

            postService.create(postSaveRequest);
        }
    }

    private String getContentsByGpt() {
        String prompt = "아무 주제나 임의로 1개를 선정하여 자유롭게 서술하시오. 반말로 해.";
        String model = "gpt-4o";

        return chatGPTService.question(prompt, model);
    }
}
