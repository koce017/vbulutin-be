package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String content;
    private TopicDTO topic;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
