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

import com.github.oligo.wechat.api.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;

/**
 * JS API 签名
 *
 * @author Rogers
 */
public class Signature {

    private static String signatureTemplate = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
    private String url;
    private String jsApiTicket;
    private String nonceStr;
    private long timestamp;

    public Signature(String jsApiTicket, String url) {
        this.jsApiTicket = jsApiTicket;
        this.url = decodeUrl(url);
        this.nonceStr = Utils.randomString(16);
        this.timestamp = System.currentTimeMillis()/1000;
    }

    public String getUrl() {
        return url;
    }

    public String sign() {
        String finalString = String.format(signatureTemplate, this.jsApiTicket,
                this.nonceStr, this.timestamp, this.url);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] sign = digest.digest(finalString.getBytes());
            return Hex.encodeHexString(sign);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Map<String, Object> getSignConfig(){
        Map<String, Object> rv = new HashMap<>();
        rv.put("signature", sign());
        rv.put("timestamp", timestamp);
        rv.put("nonceStr", this.nonceStr);
        
        return rv;
    }

    public String decodeUrl(String url) {
        try {
            String decodedUrl = URLDecoder.decode(url, "utf-8");
            if (decodedUrl.equals(url)) {
                // Url is not encoded
                return url;
            }
            return decodedUrl;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
