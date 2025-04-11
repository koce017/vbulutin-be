package com.koce017.vbulutin.data.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Long position;
    private BoardDTO board;
    private List<ForumDTO> forums;
}

