package com.yangyag.toy.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequest {
    private String title;
    private String contents;
    private String author;
}
