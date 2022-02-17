package com.yangyag.toy.domain.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yangyag.toy.domain.posts.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "replies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    private String author;

    @Lob
    private String contents;

    @NonNull
    @Column(nullable = false)
    private String pw;
}
