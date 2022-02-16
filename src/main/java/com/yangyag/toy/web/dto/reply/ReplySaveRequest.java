package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplySaveRequest {
    @NonNull
    Long postId;

    @NonNull
    String author;

    String contents;

    @NonNull
    String pw;
}
