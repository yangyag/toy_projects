package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequest {
    @NonNull
    @Column(name = "post_id")
    Long postId;

    String author;

    @Lob
    String contents;

    @NonNull
    @Column(nullable = false)
    String pw;
}
