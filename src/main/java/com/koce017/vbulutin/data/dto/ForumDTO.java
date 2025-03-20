package com.koce017.vbulutin.data.dto;

import java.util.ArrayList;
import java.util.List;

public class ForumDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Long position;
    private boolean isLocked;
    private CategoryDTO category;
    private ForumDTO parent;
    private List<ForumDTO> children = new ArrayList<>();

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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public ForumDTO getParent() {
        return parent;
    }

    public void setParent(ForumDTO parent) {
        this.parent = parent;
    }

    public List<ForumDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ForumDTO> children) {
        this.children = children;
    }
}
