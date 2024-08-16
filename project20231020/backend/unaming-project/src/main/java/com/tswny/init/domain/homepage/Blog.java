package com.tswny.init.domain.homepage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.AbstractAuditingEntity;
import com.tswny.init.domain.User;
import com.tswny.init.domain.enumeration.BlogOpenStateEnum;

import javax.persistence.*;

@Entity
public class Blog extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String category;

    private String remark;

    private String tag;

    @Enumerated(EnumType.STRING)
    private BlogOpenStateEnum openState = BlogOpenStateEnum.PRIVATE;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogOpenStateEnum getOpenState() {
        return openState;
    }

    public void setOpenState(BlogOpenStateEnum openState) {
        this.openState = openState;
    }
}
