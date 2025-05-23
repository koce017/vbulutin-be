package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private TopicDto topic;
    private UserDto poster;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
