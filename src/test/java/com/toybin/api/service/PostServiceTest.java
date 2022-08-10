package com.toybin.api.service;

import com.toybin.api.domain.Post;
import com.toybin.api.repository.PostRepository;
import com.toybin.api.request.PostCreate;
import com.toybin.api.response.PostResponse;
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
    @DisplayName("게시글 한 개 조회하기")
    void getPost() {
        //given
        Post post = Post.builder().
                title("test title").
                content("test content").
                build();

        postRepository.save(post);

        //when
        PostResponse postResponse = postService.getPost(post.getId());

        //then
        assertNotNull(postResponse);
        assertEquals("test title", postResponse.getTitle());
        assertEquals("test content", postResponse.getContent());
    }
}