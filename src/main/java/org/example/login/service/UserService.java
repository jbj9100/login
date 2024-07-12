package org.example.login.service;

import lombok.RequiredArgsConstructor;
import org.example.login.controller.dto.UserResponseDto;
import org.example.login.controller.dto.UserSignUpDto;
import org.example.login.controller.dto.UserUpdateRequestDto;
import org.example.login.domain.User;
import org.example.login.repository.MajorRepository;
import org.example.login.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MajorRepository majorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MajorRepository majorRepository) {
        this.userRepository = userRepository;
        this.majorRepository = majorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public Long join(UserSignUpDto signUpDto) {
        userRepository.findByLoginId(signUpDto.getLoginId())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Failed: Already Exist ID!");
                });

        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Failed: Please Check Password!");
        }

        User user = User.signupBuilder()
                .loginId(signUpDto.getLoginId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .phoneNumber(signUpDto.getPhoneNumber())
                .major(majorRepository.findById(signUpDto.getMajorId()).get())
                .build();
        return userRepository.save(user).getUserId();
    }

    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No User Info"));

        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No User Info"));

        user.update(requestDto);

        return userId;
    }
}