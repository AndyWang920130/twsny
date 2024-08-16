package com.tswny.init.service.dto;

import com.tswny.init.domain.enumeration.BlogOpenStateEnum;

import java.util.List;

public class BlogDTO {
    private Long id;

    private String title;

    private String content;

    private String category;

    private String remark;

    private List<String> tagList;

    private BlogOpenStateEnum openState;

    private Long userId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public BlogOpenStateEnum getOpenState() {
        return openState;
    }

    public void setOpenState(BlogOpenStateEnum openState) {
        this.openState = openState;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
