package com.tswny.init.service.dto;

import com.tswny.init.domain.User;
import com.tswny.init.web.rest.vm.UserVM;

import java.time.Instant;

public class UserDTO extends AbstractAuditingEntityDTO{
    private Long id;

    private String login;

    private String password;

    private String phone;

    private String realName;

    private Instant birthday;

    private String profile;



//    public UserDTO(User user) {
//        this.id = user.getId();
//        this.login = user.getLogin();
//        this.password = user.getPassword();
//        this.phone = user.getPhone();
//        this.realName = user.getRealName();
//        this.birthday = user.getBirthday();
//
//        this.createdBy = user.getCreatedBy();
//        this.createdDate = user.getCreatedDate();
//        this.lastModifiedBy = user.getLastModifiedBy();
//        this.lastModifiedDate = user.getLastModifiedDate();
//    }

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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
