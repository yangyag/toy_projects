package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyUpdateRequest {
    @NonNull
    Long id;

    Long postId;

    @NonNull
    String author;

    String contents;

    @NonNull
    String pw;
}
