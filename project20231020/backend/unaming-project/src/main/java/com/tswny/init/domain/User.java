package com.tswny.init.domain;

import com.tswny.init.web.rest.vm.UserVM;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

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
}
