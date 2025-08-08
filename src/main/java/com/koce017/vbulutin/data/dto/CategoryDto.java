package com.koce017.vbulutin.data.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String slug;
    private String description;
    private Long position;
    private BoardDto board;
    private List<ForumDto> forums;
}

