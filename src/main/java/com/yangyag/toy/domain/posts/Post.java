package com.yangyag.toy.domain.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "posts")
public class Post {
    @Id
    private Long id;

    @Column
    private String title;

    @Lob
    private String contents;

    private String author;
}
