package com.likelion.dub.controller;


import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class JspController {

    private final MemberService memberService;
    private final RestTemplate restTemplate;



    @GetMapping("/login")
    public String loginView() {
        String result = "loginVIew";

        return "loginView";
    }

    @GetMapping("/redirect")
    public @ResponseBody String kakaoCallback(String code) {
        String url = "http://localhost:8080/app/member/loginKakao";

        // 요청 바디 생성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("authorizationCode", code);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        System.out.println(code);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // JSONObject를 JSON 문자열로 변환하여 출력
        System.out.println(requestBody.toString());
        // HttpEntity 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);


        System.out.println("HttpEntity: " + requestEntity);
        // restTemplate을 사용하여 JSON 요청 보내기
        String response = restTemplate.postForObject(url, requestEntity, String.class);
        return "Bearer " + response;
    }

    @GetMapping("/main")
    public String mainView(Model model) {
        String result = "mainView";
        model.addAttribute("result", result);
        return "mainView";
    }

    @GetMapping("/join")
    public String joinView(Model model) {
        model.addAttribute("memberJoinRequest", new MemberJoinRequest());
        return "joinView";
    }
}
