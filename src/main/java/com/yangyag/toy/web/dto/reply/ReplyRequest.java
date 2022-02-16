package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyRequest {
    @NonNull
    Long postId;

    @NonNull
    Long id;
}
