package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Boolean isHidden;
    private UserDto owner;
    private List<CategoryDto> categories;
}
