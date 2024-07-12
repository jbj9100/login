package org.example.login.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.login.controller.dto.UserResponseDto;
import org.example.login.controller.dto.UserSignUpDto;
import org.example.login.controller.dto.UserUpdateRequestDto;
import org.example.login.domain.Major;
import org.example.login.domain.User;
import org.example.login.service.MajorService;
import org.example.login.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final MajorService majorService;

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model,
                         @RequestParam(value="msg", required = false) String msg) {
        List<Major> majors = majorService.findAll();

        model.addAttribute("signUpDto", new UserSignUpDto());
        model.addAttribute("majors", majors);
        model.addAttribute("msg", msg);
        return "signup";
    }

    @PostMapping("/signup")
    public String create(UserSignUpDto signUpDto, Model model) {
        try {
            userService.join(signUpDto);
        } catch (Exception e) {
            List<Major> majors = majorService.findAll();

            model.addAttribute("majors", majors);
            model.addAttribute("signUpDto", signUpDto);
            model.addAttribute("msg", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }

    @GetMapping("/myCourses")
    public String myCourseList(@AuthenticationPrincipal User user, Model model,
                               @RequestParam(value="msg", required = false) String msg) {
        UserResponseDto userResponseDto = userService.findById(user.getUserId());

        model.addAttribute("username", userResponseDto.getUsername());
        model.addAttribute("takeClasses", userResponseDto.getTakeClasses());
        model.addAttribute("msg", msg);

        return "myCourseList";
    }

    @GetMapping("/edit")
    public String update(@AuthenticationPrincipal User user, Model model,
                         @RequestParam(value="msg", required = false) String msg) {

        UserResponseDto userResponseDto = userService.findById(user.getUserId());
        model.addAttribute("userUpdateRequestDto", new UserUpdateRequestDto(userResponseDto));
        model.addAttribute("msg", msg);

        return "edit";
    }

    @PostMapping("/edit")
    public String userInfoUpdate(@AuthenticationPrincipal User user,
                                 UserUpdateRequestDto userUpdateRequestDto, Model model) {
        try {
            userService.update(user.getUserId(), userUpdateRequestDto);
        } catch (Exception e) {
            model.addAttribute("userUpdateRequestDto", userUpdateRequestDto);
            model.addAttribute("msg", e.getMessage());
            return "edit";
        }

        return "redirect:/";
    }
}
