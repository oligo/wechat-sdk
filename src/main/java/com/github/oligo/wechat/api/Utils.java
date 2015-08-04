/*
 * Copyright 2015. Rogers1b
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.oligo.wechat.api;

import com.github.oligo.wechat.api.accessToken.AccessToken;
import com.github.oligo.wechat.api.accessToken.AccessTokenFactory;

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
     * @param appId 微信appId
     * @param appSecret 微信appSecret
     * @return access token
     */
    public static String getAccessToken(String appId, String appSecret){
        AccessToken token = null;
        token = AccessTokenFactory.getAccessToken(appId, appSecret);
        logger.info("Get access token successfully.");
        
        return token.getToken();
    }

    /**
     * 生成随机字符串
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
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

    /**
     * 从HttpServletRequest中获取完整的请求URL
     *
     * @param request HttpServletRequest
     * @return full url
     */
    public static String getCurrentRequestUrl(HttpServletRequest request){
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();

        if(queryString == null || queryString.isEmpty()){
            return requestUrl.toString();
        }else{
            return requestUrl.append("?").append(queryString).toString();
        }
    }

    /**
     * 组装微信网页授权请求oauth2 code的URL
     *
     * @param appId 微信appId
     * @param redirectUrl oauth2回调URL
     * @param state state字符串，任意随机字符串
     * @return request url
     */
    public static String getOauth2Url(String appId, String redirectUrl, String state){
        try{
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        }catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        return String.format(oauth2UrlTpl, appId, redirectUrl, state);
    }
}
