package com.tswny.init.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.util.WeiXinCommonUtil;
import com.tswny.init.util.dto.weChat.AccessToken;
import com.tswny.init.util.dto.weChat.JsApiTicket;
import com.tswny.init.util.dto.weChat.WeixinOauth2Token;
import com.tswny.init.web.rest.vm.WeChatSignatureVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeiXinUtil {
    private static final Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appId=APPID&secret=APPSECRET";
    public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public final static String OAUTH2_LOGIN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appId=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public final static String GET_CODE_IMAGE = "https://open.weixin.qq.com/connect/oauth2/authorize?appId=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public final static String JSAPI_SIGNATURE = "jsapi_ticket=JSAPITICKET&noncestr=NONCESTRW&timestamp=TIMESTAMP&url=URL";

    public final static String APPID = "wx7d6557dc20346281";
    public final static String APPSECRET = "5743cd7a6a406fad8bd61cd60dac2537";

    private static Map<String, String> appIdAndAppSecretMap = new HashMap<>();
    static {
        appIdAndAppSecretMap.put("wx7d6557dc20346281", "5743cd7a6a406fad8bd61cd60dac2537"); // twsny
        appIdAndAppSecretMap.put("wxb8efee18f1790b70", "27dfb86a9c6a7f928a3f502a03e57919"); //AED
    }

    private static Map<String, AccessToken> tokenMap = new HashMap<>();
    private static Map<String, JsApiTicket> jsApiTicketMap = new HashMap<>();

    public static AccessToken getAccessToken(String appId) {
        AccessToken accessToken = tokenMap.get(appId);
        Long now = (new Date()).getTime();
        if (accessToken == null || now > accessToken.getExpiresIn()) {
            accessToken = requestForAccessToken(appId);
            accessToken.setExpiresIn(accessToken.getExpiresIn() + now);
            tokenMap.put(appId, accessToken);
        }

        return accessToken;
    }

    public static JsApiTicket getJsApiTicket(String appId) {
        JsApiTicket jsApiTicket = jsApiTicketMap.get(appId);
        Long now = (new Date()).getTime();
        if (jsApiTicket == null || now > jsApiTicket.getExpiresIn()) {
            AccessToken accessToken = getAccessToken(appId);
            jsApiTicket = requestForJsApiTicket(accessToken.getToken());
            jsApiTicket.setExpiresIn(accessToken.getExpiresIn() + now);
            jsApiTicketMap.put(appId, jsApiTicket);
        }
        return jsApiTicket;
    }

    /**
     * 获取TOKEN
     *
     * @return
     */
    private static JsApiTicket requestForJsApiTicket(String accessToken) {
        //替换真实appid和appsecret
        String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);

        JSONObject jsonObject = WeiXinCommonUtil.httpsRequest(requestUrl, "GET", null);
        //将得到的json对象的属性值，存到accesstoken中
        JsApiTicket jsApiTicket = new JsApiTicket();
        jsApiTicket.setTicket(jsonObject.getString("ticket"));
        jsApiTicket.setExpiresIn(Long.valueOf(jsonObject.getIntValue("expires_in")));
        return jsApiTicket;
    }


    /**
     * 获取TOKEN
     *
     * @return
     */
    private static AccessToken requestForAccessToken(String appId) {
        String appSecret = appIdAndAppSecretMap.get(appId);
        //替换真实appid和appsecret
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        AccessToken accesstoken = new AccessToken();
        //得到json对象
        JSONObject jsonObject = WeiXinCommonUtil.httpsRequest(requestUrl, "GET", null);

        //将得到的json对象的属性值，存到accesstoken中
        accesstoken.setToken(jsonObject.getString("access_token"));
        accesstoken.setExpiresIn(Long.valueOf(jsonObject.getIntValue("expires_in")));
        return accesstoken;
    }

    public static String generateWeChatSignatureRawData(WeChatSignatureVM weChatSignatureVM) {
        return JSAPI_SIGNATURE
                .replace("JSAPITICKET", weChatSignatureVM.getJsapi_ticket())
                .replace("NONCESTR", weChatSignatureVM.getNoncestr())
                .replace("TIMESTAMP", weChatSignatureVM.getTimestamp().toString())
                .replace("URL", weChatSignatureVM.getUrl());
    }

    public static void main(String[] args) {
        AccessToken accessToken1 = getAccessToken("wxb8efee18f1790b70");
        System.out.println(accessToken1);

        AccessToken accessToken2 = getAccessToken(APPID);
        System.out.println(accessToken2);
    }

}
