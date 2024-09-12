package com.tswny.init.web.rest.vm;

import com.tswny.init.domain.enumeration.BlogLikeStateEnum;

public class BlogLikeVM {
    private Long id;
    private BlogLikeStateEnum likeState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlogLikeStateEnum getLikeState() {
        return likeState;
    }

    public void setLikeState(BlogLikeStateEnum likeState) {
        this.likeState = likeState;
    }
}
