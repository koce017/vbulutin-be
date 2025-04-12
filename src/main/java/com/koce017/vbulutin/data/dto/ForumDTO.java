package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Long position;
    private Boolean isLocked;
    private PostDTO lastPost;
    private CategoryDTO category;
    private ForumDTO parent;
    private List<TopicDTO> topics;
    private List<ForumDTO> children;
}
