package com.koce017.vbulutin.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private long id;
    private String title;
    private String slug;
    private String description;
    @JsonProperty("isVisible")
    private boolean isVisible;
    private UserDTO owner;
    private List<CategoryDTO> categories;
}
