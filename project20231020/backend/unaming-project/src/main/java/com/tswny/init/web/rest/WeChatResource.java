package com.tswny.init.web.rest;

import com.tswny.init.service.ThirdPartyWeChatService;
import com.tswny.init.web.rest.vm.WeChatSignatureVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/third-party")
public class WeChatResource {
    private final ThirdPartyWeChatService thirdPartyWeChatService;

    public WeChatResource(ThirdPartyWeChatService thirdPartyWeChatService) {
        this.thirdPartyWeChatService = thirdPartyWeChatService;
    }

    @GetMapping("/weChat/jsApiTicket/{appId}")
    public ResponseEntity<String> getJsApiTicket(@PathVariable String appId) {
        String signatureValue = thirdPartyWeChatService.getJsApiTicket(appId);
        return ResponseEntity.ok(signatureValue);
    }

    @PostMapping("/weChat/signature")
    public ResponseEntity<String> wechatSignature(@RequestBody WeChatSignatureVM weChatSignatureVM) {
        String signatureValue = thirdPartyWeChatService.wechatSignature(weChatSignatureVM);
        return ResponseEntity.ok(signatureValue);
    }
}
