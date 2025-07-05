package com.koce017.vbulutin.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank
    private String content;

    @NotNull
    private TopicDto topic;

    private UserDto poster;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

}
