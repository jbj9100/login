package org.example.login.controller;

import org.example.login.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HttpSession httpSession;
    @PreAuthorize("permitAll()")
    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("{}", user);

        if (user != "anonymousUser") {
            model.addAttribute("user", (User) user);
        }

        return "home";
    }
}
