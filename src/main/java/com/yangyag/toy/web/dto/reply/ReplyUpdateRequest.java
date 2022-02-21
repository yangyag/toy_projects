package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyUpdateRequest {
    @NotNull
    String author;

    String contents;

    @NotNull
    String pw;
}
