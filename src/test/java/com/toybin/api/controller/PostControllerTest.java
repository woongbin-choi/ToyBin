package com.toybin.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toybin.api.domain.Post;
import com.toybin.api.repository.PostRepository;
import com.toybin.api.request.PostCreate;
import com.toybin.api.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Post 요청 시 DB에 값이 저장된다")
    void writePost() throws Exception {
        //given
        PostCreate request = PostCreate.builder().
                title("Binco title").content("Binco content").build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("Binco title",post.getTitle());
        assertEquals("Binco content",post.getContent());
    }

    @Test
    @DisplayName("Id 값으로 게시글 한 개 조회")
    void getPost() throws Exception {
        //given
        Post post = Post.builder().
                title("test title").
                content("test content").
                build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                    .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.content").value("test content"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 제목 수정 테스트")
    void editPost() throws Exception {
        //given
        Post post = Post.builder().
                title("test title")
                .content("test content")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder().
                title("edit title").
                content("test content").
                build();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit))
        )
                .andExpect(status().isOk())
                .andDo(print());


    }

}