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

package net.rogers1b.wechat.api.user;

import com.fasterxml.jackson.databind.JsonNode;
import net.rogers1b.wechat.api.WeiXinApiInvoker;
import net.rogers1b.wechat.exception.WeiXinApiException;

import java.io.IOException;

/**
 *
 * @author Rogers
 */
public class OAuth2User {
    private String accessToken;
    private String openId;
    private int expireTime;
    private String scope;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    private static final String ACCESS_TOKEN_REQUEST_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?"
                    + "appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static String getUrl(String appId, String appSecret, String code){
        return String.format(ACCESS_TOKEN_REQUEST_URL, appId, appSecret, code);
    }

    public static OAuth2User build(String appId, String appSecret, String code) throws WeiXinApiException{
        JsonNode jsonResult;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonResult = invoker.doRequest(getUrl(appId, appSecret, code));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        OAuth2User o = new OAuth2User();
        o.setAccessToken(jsonResult.get("access_token").asText());
        o.setExpireTime(jsonResult.get("expires_in").asInt());
        o.setOpenId(jsonResult.get("openid").asText());
        o.setRefreshToken(jsonResult.get("refresh_token").asText());
        o.setScope(jsonResult.get("scope").asText());

        return o;
    }
}
