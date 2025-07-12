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
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isHidden = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Builder.Default
    @OrderBy("position ASC")
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

}
