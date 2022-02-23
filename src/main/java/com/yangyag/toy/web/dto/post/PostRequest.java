package com.yangyag.toy.web.dto.post;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Long id;
    private String title;
    private String contents;
    private String author;
}
