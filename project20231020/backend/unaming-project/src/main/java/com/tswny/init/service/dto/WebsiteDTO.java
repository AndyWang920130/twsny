package com.tswny.init.service.dto;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.Website;
import com.tswny.init.util.EncryptUtil;

public class WebsiteDTO extends AbstractAuditingEntityDTO{
    private Long id;

    private String url;

    private String loginName;

    private String password;

    private String description;

    private String directory;

    public WebsiteDTO(Website website) {
        String url = website.getUrl();
        if (!StringUtils.isNullOrEmpty(url)) {
            this.setUrl(EncryptUtil.base64Decrypt(url));
        }

        this.setLoginName(website.getLoginName());
        this.setPassword(website.getPassword());
        this.setDescription(website.getDescription());
        this.setDirectory(website.getDirectory());
        this.setCreatedBy(website.getCreatedBy());
        this.setCreatedDate(website.getCreatedDate());
        this.setLastModifiedBy(website.getLastModifiedBy());
        this.setLastModifiedDate(website.getLastModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
