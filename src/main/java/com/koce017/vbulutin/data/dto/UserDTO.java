package com.koce017.vbulutin.data.dto;

import com.koce017.vbulutin.data.entity.Board;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<Board> boards;
}
