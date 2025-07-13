package com.koce017.vbulutin.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardTreeNode {
    private String id;
    private String text;
    private List<BoardTreeNode> children;
}
