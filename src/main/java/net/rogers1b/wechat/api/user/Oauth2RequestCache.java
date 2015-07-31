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
