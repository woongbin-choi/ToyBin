package com.toybin.api.service;

import com.toybin.api.domain.Post;
import com.toybin.api.repository.PostRepository;
import com.toybin.api.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 작성하기")
    void write() {
        //given
        PostCreate postCreate = PostCreate.builder().
                title("ToyBin title").
                content("ToyBin content").
                build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("ToyBin title", post.getTitle());
        assertEquals("ToyBin content", post.getContent());
    }

    @Test
    void getPost() {
    }
}