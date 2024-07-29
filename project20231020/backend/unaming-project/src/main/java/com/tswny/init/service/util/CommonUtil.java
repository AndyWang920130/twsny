package com.tswny.init.service.util;

import com.tswny.init.domain.homepage.Blog;
import com.tswny.init.web.rest.vm.BlogVM;
import org.apache.commons.lang3.StringUtils;

public class CommonUtil {
    private final static int DEFAULT_REMARK_LENGTH = 100;
    public static String generateBlogRemark(BlogVM blogVM) {
        String content = blogVM.getContent();
        if (StringUtils.isNotBlank(content)) {
            return content.length() > DEFAULT_REMARK_LENGTH ? content.substring(0, DEFAULT_REMARK_LENGTH) : content;
        }
        return StringUtils.EMPTY;
    }
}
