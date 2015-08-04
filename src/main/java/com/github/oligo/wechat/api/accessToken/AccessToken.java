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

package com.github.oligo.wechat.api.accessToken;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.oligo.wechat.api.WeiXinApiInvoker;
import com.github.oligo.wechat.exception.WeiXinApiException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rogers on 14-6-10.
 */

/**
 * Wechat AccessToken object
 */
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appId;
    private String token;
    private Date expireTime;
    private Date retrieveTime;

    /**
     * test to see if token has expired
     * @return boolean
     */
    public boolean hasExpired(){
        Date now = new Date();
        return now.after(getExpireTime());
    }

    public String getAppId() {
        return appId;
    }

    void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getRetrieveTime() {
        return retrieveTime;
    }

    void setRetrieveTime(Date retrieveTime) {
        this.retrieveTime = retrieveTime;
    }


    private static final String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String grantType = "client_credential";

    private static String getUrl(String appId, String appSecret){
        return ACCESS_TOKEN_API + String.format("?grant_type=%s&appid=%s&secret=%s",
                grantType, appId, appSecret);
    }

    /**
     * 从微信服务器请求AccessToken
     *
     * @param appId 微信appId
     * @param appSecret 微信appSecret
     * @return AccessToken
     * @throws WeiXinApiException 微信API异常，详细原因可以查询微信开发者文档
     */
    public static AccessToken build(String appId, String appSecret) throws WeiXinApiException{
        JsonNode jsonResult;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonResult = invoker.doRequest(getUrl(appId, appSecret));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        Calendar calendar = Calendar.getInstance();
        Date retrieveTime = calendar.getTime();

        calendar.add(Calendar.SECOND, jsonResult.get("expires_in").asInt());
        Date expireTime = calendar.getTime();

        AccessToken at = new AccessToken();
        at.setAppId(appId);
        at.setToken(jsonResult.get("access_token").asText());
        at.setRetrieveTime(retrieveTime);
        at.setExpireTime(expireTime);

        return at;
    }

    @Override
    public String toString() {
        return "AccessToken{" + "appId=" + appId + ", token=" + token +
                ", expireTime=" + expireTime + ", retrieveTime=" + retrieveTime + '}';
    }
}
