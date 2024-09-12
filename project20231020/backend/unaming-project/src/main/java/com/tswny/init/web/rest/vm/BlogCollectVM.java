package com.tswny.init.web.rest.vm;

import com.tswny.init.domain.enumeration.BlogCollectStateEnum;

public class BlogCollectVM {
    private Long id;
    private BlogCollectStateEnum collectState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlogCollectStateEnum getCollectState() {
        return collectState;
    }

    public void setCollectState(BlogCollectStateEnum collectState) {
        this.collectState = collectState;
    }
}
