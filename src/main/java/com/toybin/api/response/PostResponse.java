package com.toybin.api.response;

import com.toybin.api.domain.Post;
import com.toybin.api.repository.PostRepository;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;

    private final String title;

    private final String content;

    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
