package com.yangyag.toy.web.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostUpdateRequest {
    private Long id;
    private String title;
    private String contents;
    private String author;
}
