package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String title;
    private String slug;
    private Boolean isLocked;
    private PostDto lastPost;
    private LocalDateTime deletedAt;
    private ForumDto forum;
    private List<PostDto> posts;
}
