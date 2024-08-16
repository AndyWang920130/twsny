package com.tswny.init.config;

import com.tswny.init.web.rest.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomFileHandlerConfiguration implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(CustomFileHandlerConfiguration.class);

    @Value("${file.upload.location}")
    public String location;

    /**
     * 用于配置静态资源请求, 指定某个路径模式下的请求（即 /api/v1/resources/**）
     * 应该从指定的资源位置（location）加载静态资源。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有F:/resources/目录下的资源,访问时都映射到/res/** 路径下
        log.info("location: " + location);
        registry.addResourceHandler("/api/v1/resources/**")
                .addResourceLocations(location);
    }
}
