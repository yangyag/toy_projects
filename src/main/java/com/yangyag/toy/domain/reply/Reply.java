package com.yangyag.toy.domain.reply;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "replys")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "post_id", nullable = false)
    Long postId;

    String author;

    @Lob
    String contents;

    @NonNull
    @Column(nullable = false)
    String pw;
}
