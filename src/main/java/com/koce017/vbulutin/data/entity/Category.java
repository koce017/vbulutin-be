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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Lob
    private String description;

    @Column(nullable = false)
    private long position;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Builder.Default
    @OrderBy("position ASC")
    @OneToMany(mappedBy = "category")
    private List<Forum> forums = new ArrayList<>();

}
