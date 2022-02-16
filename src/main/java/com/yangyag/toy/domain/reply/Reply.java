package com.yangyag.toy.domain.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "replys")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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
