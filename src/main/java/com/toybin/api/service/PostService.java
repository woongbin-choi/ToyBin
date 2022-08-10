package com.toybin.api.service;

import com.toybin.api.domain.Post;
import com.toybin.api.domain.PostEditor;
import com.toybin.api.repository.PostRepository;
import com.toybin.api.request.PostCreate;
import com.toybin.api.request.PostEdit;
import com.toybin.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        Post post = Post.builder().
                title(postCreate.getTitle()).
                content(postCreate.getContent()).
                build();

        postRepository.save(post);
    }

    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id).
                orElseThrow(IllegalArgumentException::new);

        return PostResponse.builder().
                id(post.getId()).
                title(post.getTitle()).
                content(post.getContent()).
                build();
    }

    public List<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).
                stream().map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editPost(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id).
                orElseThrow(IllegalArgumentException::new);

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle()).
                content(postEdit.getContent()).build();

        post.edit(postEditor);
    }

    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);

    }

}
