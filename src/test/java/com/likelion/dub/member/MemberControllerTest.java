package com.likelion.dub.member;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.dub.token.domain.KakaoLoginParams;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {


    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    private static final String authorizationCode = "4lKRW_1z0BdjGGEQrYSoAZY_hUZt1BmoxebFeR44jqjww27vcmVgKZpCvik3odo2dmxWgwopyWAAAAGK19gfBQ";

    @Test
    public void 카카오_로그인_회원가입() throws Exception {
        //given
        KakaoLoginParams params = new KakaoLoginParams(authorizationCode);
        //when
        ResultActions resultActions = mvc.perform(post("/app/member/loginKakao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.is_Success").value(true))
                .andExpect(jsonPath("$.code").value(1000));


    }


}