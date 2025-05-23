package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumDto {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Long position;
    private Boolean isLocked;
    private PostDto lastPost;
    private CategoryDto category;
    private ForumDto parent;
    private List<TopicDto> topics;
    private List<ForumDto> children;
}
