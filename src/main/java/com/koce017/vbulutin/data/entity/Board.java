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

    @Lob
    private String description;

    @Column(nullable = false)
    private Boolean isHidden;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Builder.Default
    @OrderBy("position ASC")
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

}
