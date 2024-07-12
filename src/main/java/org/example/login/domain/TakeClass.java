package org.example.login.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Getter
public class TakeClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "take_id", nullable = false)
    private Long takeId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Classes.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Classes classes;

    @Builder
    public TakeClass(User user, Classes classes) {
        this.user = user;
        this.classes = classes;
    }
}
