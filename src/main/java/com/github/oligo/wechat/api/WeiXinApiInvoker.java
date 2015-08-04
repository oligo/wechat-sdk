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

package com.github.oligo.wechat.api;

/**
 * Created by Rogers on 14-6-10.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.oligo.wechat.exception.WeiXinApiException;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WeiXin API请求，失败后会重试
 */
public class WeiXinApiInvoker {
    private static int retryInternalInMillis = 1000; // 1000 milliseconds
    private static int maxRetryTimes = 5;

    protected static final Logger logger = LoggerFactory.getLogger(WeiXinApiInvoker.class);

    public WeiXinApiInvoker() {
    }

    /**
     * 请求微信API
     *
     * 请求失败后，会根据接口返回状态，判断是否需要尝试新的请求，尝试次数为5次。
     * @param url 微信接口URL
     * @return T
     * @throws java.io.IOException HTTP请求异常
     * @throws com.github.oligo.wechat.exception.WeiXinApiException 微信API异常
     */
    public JsonNode doRequest(String url) throws IOException, WeiXinApiException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        int retryTimes = 0;
        do {
            try {
                CloseableHttpResponse response = httpClient.execute(request);
                HttpEntity rspEntity = response.getEntity();
                logger.debug("Wx API Request successfully executed");
                return parseResult(rspEntity);
            } catch (WeiXinApiException e) {
                if (e.getErrorCode() == -1) {
                    // 系统繁忙
                    int sleepMillis = retryInternalInMillis * (1 << retryTimes);
                    try {
                        logger.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
        } while (++retryTimes < maxRetryTimes);

        httpClient.close();
        throw new RuntimeException("微信API异常，超出重试次数!");
    }

    private JsonNode parseResult(final HttpEntity entity) throws WeiXinApiException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = null;

        try {
            jsonObject = mapper.readTree(entity.getContent());
        } catch (IOException e) {
            logger.error("Parsing json failed!");
            throw new RuntimeException(e);
        }
        // API error to exception
        WeiXinApiException exception = toException(jsonObject);
        if (exception != null) {
            throw exception;
        }
        return jsonObject;
    }

    private WeiXinApiException toException(JsonNode json) {
        if (json.isNull()) {
            return null;
        }
        ObjectNode node = (ObjectNode) json;
        if (node.hasNonNull("errcode") && node.hasNonNull("errmsg")) {
            if (node.get("errcode").asInt() != 0) {
                return new WeiXinApiException(node.get("errmsg").textValue(),
                        node.get("errcode").asInt());
            }
        }

        return null;
    }
}
