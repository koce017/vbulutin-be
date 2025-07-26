package com.koce017.vbulutin.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forums")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long position;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isLocked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Forum parent;

    @Builder.Default
    @OneToMany(mappedBy = "forum")
    private List<Topic> topics = new ArrayList<>();

    @Builder.Default
    @OrderBy("position ASC")
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Forum> children = new ArrayList<>();

}
