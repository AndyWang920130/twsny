package com.tswny.init.domain.homepage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.AbstractAuditingEntity;
import com.tswny.init.domain.User;
import com.tswny.init.domain.enumeration.BlogOpenStateEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnoreProperties(value = "blogsCollected", allowSetters = true)
    @ManyToMany
    @JoinTable(
            name = "blog_users_collected",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_collected_id")
    )
    private Set<User> usersCollected = new HashSet<>();

    @JsonIgnoreProperties(value = "blogsLiked", allowSetters = true)
    @ManyToMany
    @JoinTable(
            name = "blog_users_liked",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_liked_id")
    )
    private Set<User> usersLiked = new HashSet<>();

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

    public Set<User> getUsersCollected() {
        return usersCollected;
    }

    public void setUsersCollected(Set<User> usersCollected) {
        this.usersCollected = usersCollected;
    }

    public Set<User> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(Set<User> usersLiked) {
        this.usersLiked = usersLiked;
    }
}
