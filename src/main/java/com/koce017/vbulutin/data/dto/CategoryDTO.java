package com.koce017.vbulutin.data.dto;


import java.util.List;

public class CategoryDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private long position;
    private BoardDTO board;
    private List<ForumDTO> forums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public List<ForumDTO> getForums() {
        return forums;
    }

    public void setForums(List<ForumDTO> forums) {
        this.forums = forums;
    }
}
