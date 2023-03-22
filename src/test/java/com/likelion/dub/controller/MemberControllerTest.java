package com.likelion.dub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void join() throws Exception
    {
        String email = "fdsafdsa";
        String username = "hi";
        String password = "1234";
        Long stu_num = 1234L;
        mockMvc.perform(post("/app/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest(email, username, password, stu_num))))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원가입 실패")
    void join_fail() throws Exception
    {
        String email = "fdsafdsa";
        String username = "hi";
        String password = "1234";
        Long stu_num = 1234L;
        mockMvc.perform(post("/app/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinRequest(email, username, password, stu_num))))
                .andDo(print())
                .andExpect(status().isConflict());

    }
}