package com.yangyag.toy.domain.posts;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 717836088L;

    public static final QPost post = new QPost("post");

    public final StringPath author = createString("author");

    public final StringPath contents = createString("contents");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.yangyag.toy.domain.reply.Reply, com.yangyag.toy.domain.reply.QReply> replies = this.<com.yangyag.toy.domain.reply.Reply, com.yangyag.toy.domain.reply.QReply>createList("replies", com.yangyag.toy.domain.reply.Reply.class, com.yangyag.toy.domain.reply.QReply.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

