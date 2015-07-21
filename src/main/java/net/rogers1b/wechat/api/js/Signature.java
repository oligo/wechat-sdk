/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rogers1b.wechat.api.js;

import net.rogers1b.wechat.api.Utils;

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
        this.url = url;
        this.nonceStr = Utils.randomString(16);
        this.timestamp = System.currentTimeMillis()/1000;
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
}
