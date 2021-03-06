package com.yangyag.toy.web.dto.post;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostUpdateRequest {
    private String title;
    private String contents;
    private String author;
}
