package com.toybin.api.controller;

import com.toybin.api.request.PostCreate;
import com.toybin.api.request.PostEdit;
import com.toybin.api.response.PostResponse;
import com.toybin.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void addPost(@RequestBody @Valid PostCreate postCreate){
        postService.write(postCreate);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getAllPosts(@PageableDefault(size = 5) Pageable pageable){
        return postService.getAllPosts(pageable);
    }

    @PatchMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit){
        postService.editPost(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }


}
