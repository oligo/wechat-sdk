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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.oligo.wechat.api.WeiXinApiInvoker;
import com.github.oligo.wechat.exception.WeiXinApiException;

import java.io.IOException;

/**
 *
 * @author Rogers
 */
public class JsTicket {
    private String ticket;
    private int expiryTime; // in seconds

    private static String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"
            + "?access_token=%s&type=jsapi";

    public JsTicket(String ticket, int expiryTime) {
        this.ticket = ticket;
        this.expiryTime = expiryTime;
    }

    public String getTicket() {
        return ticket;
    }

    public int getExpiryTime() {
        return expiryTime;
    }

    private static String getUrl(String accessToken){
        return String.format(url, accessToken);
    }

    public static JsTicket build(String accessToken) throws WeiXinApiException{
        JsonNode jsonNode;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonNode = invoker.doRequest(getUrl(accessToken));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        JsTicket response = new JsTicket(
                jsonNode.get("ticket").asText(),
                jsonNode.get("expires_in").asInt());

        return response;
    }
}
