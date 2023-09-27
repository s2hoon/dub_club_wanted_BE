package com.likelion.dub.controller;


import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*") //Cors 제거
@RequestMapping("/app/jsp")
public class JspController {

    private final MemberService memberService;
    private final RestTemplate restTemplate;



    @RequestMapping("/login")
    public String loginView() {
        return "loginView";
    }

    @GetMapping("/redirect")
    public String kakaoCallback(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        // code 값을 main 페이지로 전달
        redirectAttributes.addFlashAttribute("authorizationCode", code);
        return "redirect:/mainView"; // mainView 페이지로 리다이렉트
    }

    @GetMapping("/mainView")
    public String mainView() {
        return "mainView";
    }

    @GetMapping("/join")
    public String joinView(Model model) {
        model.addAttribute("memberJoinRequest", new MemberJoinRequest());
        return "joinView";
    }
}
