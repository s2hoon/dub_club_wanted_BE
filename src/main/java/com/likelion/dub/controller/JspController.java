package com.likelion.dub.controller;

import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.dto.MemberJoinRequest;
import com.likelion.dub.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JspController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginView(Model model) {
        String result = "loginVIew";
        model.addAttribute("result", result);
        return "loginView";
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
