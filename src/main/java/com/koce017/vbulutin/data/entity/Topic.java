package com.koce017.vbulutin.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isLocked = false;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Forum forum;

    @Builder.Default
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
