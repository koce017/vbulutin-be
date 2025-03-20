package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private boolean isVisible;
    private UserDTO owner;
    private List<CategoryDTO> categories;
}
