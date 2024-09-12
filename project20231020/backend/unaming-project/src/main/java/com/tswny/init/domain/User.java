package com.tswny.init.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.web.rest.vm.UserVM;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

// @JsonIgnoreProperties(value = {"publishers", "subscribers"})
@Entity
public class User extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    private String phone;

    private String realName;

    private Instant birthday;

    private String profile;

    @JsonIgnoreProperties(value = "subscribers", allowSetters = true)
    @ManyToMany
    @JoinTable(
            name = "user_subscribers",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<User> publishers = new HashSet<>();

    // @JoinTable：指定中间表的名称以及外键列的名称。
    // joinColumns 是当前实体的外键列，
    // inverseJoinColumns 是关联实体的外键列。
    @JsonIgnoreProperties(value = "publishers", allowSetters = true)
    @ManyToMany (mappedBy = "publishers")
//    @JoinTable(
//            name = "user_publishers",
//            joinColumns = @JoinColumn(name = "publisher_id"),
//            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
//    )
    private Set<User> subscribers = new HashSet<>();


    @JsonIgnoreProperties(value = "usersLiked", allowSetters = true)
    @ManyToMany (mappedBy = "usersLiked")
    private Set<Blog> blogsLiked = new HashSet<>();

    @JsonIgnoreProperties(value = "usersCollected", allowSetters = true)
    @ManyToMany (mappedBy = "usersCollected")
    private Set<Blog> blogsCollected = new HashSet<>();

    public User() {}

    public User(UserVM userVM) {
        this.id = userVM.getId();
        this.login = userVM.getLogin();
        this.password = userVM.getPassword();
        this.phone = userVM.getPhone();
        this.realName = userVM.getRealName();
        this.birthday = userVM.getBirthday();

        this.createdBy = userVM.getCreatedBy();
        this.createdDate = userVM.getCreatedDate();
        this.lastModifiedBy = userVM.getLastModifiedBy();
        this.lastModifiedDate = userVM.getLastModifiedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<User> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<User> publishers) {
        this.publishers = publishers;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Set<Blog> getBlogsLiked() {
        return blogsLiked;
    }

    public void setBlogsLiked(Set<Blog> blogsLiked) {
        this.blogsLiked = blogsLiked;
    }

    public Set<Blog> getBlogsCollected() {
        return blogsCollected;
    }

    public void setBlogsCollected(Set<Blog> blogsCollected) {
        this.blogsCollected = blogsCollected;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
