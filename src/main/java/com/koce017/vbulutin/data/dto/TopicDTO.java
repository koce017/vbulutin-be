package com.koce017.vbulutin.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.koce017.vbulutin.data.entity.Forum;
import com.koce017.vbulutin.data.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private long id;
    private String title;
    private String slug;
    @JsonProperty("isLocked")
    private boolean isLocked;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Forum forum;
    private Post solution;
    private List<PostDTO> posts;
}
