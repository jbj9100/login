package org.example.login.service;

import org.example.login.domain.Classes;
import org.example.login.domain.TakeClass;
import org.example.login.domain.User;
import org.example.login.repository.ClassesRepository;
import org.example.login.repository.TakeClassRepository;
import org.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TakeClassService {

    private final TakeClassRepository takeClassRepository;
    private final UserRepository userRepository;
    private final ClassesRepository classesRepository;

    @Transactional
    public Long save(Long userId, Long classId) {
        User user = userRepository.findById(userId).get();
        Classes classes = classesRepository.findById(classId).get();

        if (classes.isFull()) throw new IllegalArgumentException("Failed: Full");

        Optional<TakeClass> any = user.getTakeClasses().stream()
                .filter(takeClass ->
                        takeClass.getClasses().getCourse().getCourseId().equals(classes.getCourse().getCourseId())).findAny();

        if (any.isPresent()) throw new IllegalArgumentException("Failed: Already Registered!");

        TakeClass takeClass = takeClassRepository.save(
                TakeClass.builder()
                    .user(user)
                    .classes(classes)
                    .build());

        user.registration(takeClass);
        classes.registration();

        return takeClass.getTakeId();
    }

    @Transactional
    public void delete(Long takeId) {
        TakeClass takeClass = takeClassRepository.findById(takeId).get();

        User user = userRepository.findById(takeClass.getUser().getUserId()).get();
        user.cancel(takeClass);

        Classes classes = classesRepository.findById(takeClass.getClasses().getClassId()).get();
        classes.cancel();

        takeClassRepository.delete(takeClass);
    }
}
