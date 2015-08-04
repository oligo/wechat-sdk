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
package com.github.oligo.wechat.api.js;

import com.github.oligo.wechat.api.accessToken.AccessTokenFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.github.oligo.wechat.api.accessToken.AccessToken;
import com.github.oligo.wechat.exception.WeiXinApiException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rogers
 */
public class JSTicketFactory {
    
    private static final Cache<String, String> cache =
            CacheBuilder.newBuilder()
                .maximumSize(10).expireAfterWrite(7100, TimeUnit.SECONDS)
                .build();
    
    public static String getJsTicket(String appId, String appSecret){
        return ticketGetter(appId, appSecret);
    }
    
    //客户端主动刷新js-ticket
    public static String refreshTicket(String appId, String appSecret){
        cache.invalidate(appId);
        return getJsTicket(appId, appSecret);
    }

    private static String ticketGetter(final String appId, final String appSecret){
        //缓存7200秒
        try{
            return cache.get(appId, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    AccessToken at = null;
                    JsTicket jr = null;
                    try{
                        at = AccessTokenFactory.getAccessToken(appId, appSecret);
                        jr = JsTicket.build(at.getToken());
                    }catch(WeiXinApiException e){
                        if(e.getErrorCode() == 42001 || e.getErrorCode() == 4001){
                            // access token 超时或无效
                            at = AccessTokenFactory.refreshToken(appId, appSecret);
                            jr = JsTicket.build(at.getToken());
                        }
                    }
                    
                    return jr.getTicket();
                }
            });
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
    }
}
