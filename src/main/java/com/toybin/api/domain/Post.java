package com.toybin.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String title;

    @Lob
    public String content;

    @Builder
    public Post(String title, String content){
        this.title = title;
        this.content= content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder().
                title(title).
                content(content);
    }

    public void edit(PostEditor postEditor){
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }


}
