package com.toybin.api.controller;

import com.toybin.api.request.PostCreate;
import com.toybin.api.request.PostEdit;
import com.toybin.api.response.PostResponse;
import com.toybin.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PatchMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit){
        postService.editPost(postId, postEdit);
    }


}
