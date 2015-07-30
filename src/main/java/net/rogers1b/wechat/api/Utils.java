/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rogers1b.wechat.api;

import net.rogers1b.wechat.api.accessToken.AccessToken;
import net.rogers1b.wechat.api.accessToken.AccessTokenFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rogers
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private static String oauth2UrlTpl = 
            "https://open.weixin.qq.com/connect/oauth2/authorize?"
            + "appid=%s"
            + "&redirect_uri=%s"
            + "&response_type=code&scope=snsapi_base"
            + "&state=%s#wechat_redirect";
    
    /**
     * AccessToken helper
     * @param appId
     * @param appSecret
     * @return 
     */
    public static String getAccessToken(String appId, String appSecret){
        AccessToken token = null;
        token = AccessTokenFactory.getAccessToken(appId, appSecret);
        logger.info("Get access token successfully.");
        
        return token.getToken();
    }
    
    
    public static String randomString(int length){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for(int i=0; i< length; i++){
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        
        return sb.toString();
    }

    public static String getCurrentRequestUrl(HttpServletRequest request){
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();

        if(queryString == null || queryString.isEmpty()){
            return requestUrl.toString();
        }else{
            return requestUrl.append("?").append(queryString).toString();
        }
    }

    public static String getOauth2Url(String appId, String redirectUrl, String state){
        try{
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        }catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        return String.format(oauth2UrlTpl, appId, redirectUrl, state);
    }
}
