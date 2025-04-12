package com.koce017.vbulutin.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private Long id;
    private String title;
    private String slug;
    @JsonProperty("isLocked")
    private Boolean isLocked;
    private PostDTO lastPost;
    private LocalDateTime deletedAt;
    private ForumDTO forum;
    private List<PostDTO> posts;
}
