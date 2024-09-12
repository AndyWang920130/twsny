package com.tswny.init.service.vm;

import com.tswny.init.domain.enumeration.SubscribeStateEnum;

public class SubscribeVM {
    private String subscriberName;
    private String publisherName;

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
