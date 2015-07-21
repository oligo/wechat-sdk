/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rogers1b.wechat.api.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rogers
 */
public class Oauth2RequestCache {
    private static final Cache<String, String> cache =
            CacheBuilder.newBuilder()
                .maximumSize(10000).expireAfterWrite(300, TimeUnit.SECONDS)
                .build();
    
    /**
     * Before redirect set request url:
     * @param state random string
     * @return 
     */
    public static void setUrl(String state, String url){
        cache.put(state, url);
    }
    
    /**
     * After redirect, get the original url.
     * @param state random string set before redirect
     * @return 
     */
    public static String getOriginalUrl(String state){
        return cache.getIfPresent(state);
    }
    
}
