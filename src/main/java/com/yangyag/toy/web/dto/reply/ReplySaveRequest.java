package com.yangyag.toy.web.dto.reply;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplySaveRequest {
    @NotNull
    String author;

    String contents;

    @NotNull
    String pw;
}
