package org.example.login.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id", nullable = false)
    private Long majorId;

    @Column(nullable = false)
    private String majorName;

    @Builder
    public Major(String majorName) {
        this.majorName = majorName;
    }
}
