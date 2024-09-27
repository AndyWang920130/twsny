package com.tswny.init.service;

import com.tswny.init.config.SecurityConfig;
import com.tswny.init.util.WeiXinUtil;
import com.tswny.init.util.signature.SignatureUtil;
import com.tswny.init.web.rest.vm.WeChatSignatureVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThirdPartyWeChatService {
    private final Logger log = LoggerFactory.getLogger(ThirdPartyWeChatService.class);

    public String getJsApiTicket(String appId) {
        String ticket =  WeiXinUtil.getJsApiTicket(appId).getTicket();
        log.info("getJsApiTicket: {}", ticket);
        return ticket;
    }

    public String wechatSignature(WeChatSignatureVM weChatSignatureVM) {
        String rawStr = WeiXinUtil.generateWeChatSignatureRawData(weChatSignatureVM);
        String sha1Str =  SignatureUtil.sha1(rawStr);
        log.info("wechatSignature: {}", sha1Str);
        return sha1Str;
    }
}
