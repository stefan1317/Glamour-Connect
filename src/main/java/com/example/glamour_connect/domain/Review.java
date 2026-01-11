package com.example.glamour_connect.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "makeup_id")
    private Makeup makeup;

    @Column
    private int rating;

    @Column
    private String comment;

    @Column
    private LocalDateTime createdAt;
}
