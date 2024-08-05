package com.example.table.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자일 경우
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 사용자는 한 가지 역할만 가질 수 있음
            String role = authorities.iterator().next().getAuthority();

            // Model에 username과 role 추가
            model.addAttribute("username", username);
            model.addAttribute("role", role);
        }
        return "main"; // main.html로 이동
    }

}