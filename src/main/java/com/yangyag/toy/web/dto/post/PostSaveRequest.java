package com.yangyag.toy.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequest {
    private String title;
    private String contents;
    private String author;
}
