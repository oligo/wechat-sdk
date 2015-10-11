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

package com.github.oligo.wechat;

import com.google.common.base.Strings;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Rogers on 15/10/12.
 */
public class Helper {
    /**
     * 如果JVM有设置http.proxyHost, http.proxyPort属性，则从此变量读取代理设置，并使用此代理设置来请求;
     * 如果操作系统有设置http_proxy环境变量，则从此环境变量读取代理设置，并使用此代理设置来请求;
     * JVM设置优先
     * @return apache http client
     */
    public static CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = null;

        String proxyHost = System.getProperty("http.proxyHost");
        String proxyPort = System.getProperty("http.proxyPort");
        // non-empty proxyHost and non-empty proxyPort
        if((!Strings.isNullOrEmpty(proxyHost)) && (!Strings.isNullOrEmpty(proxyPort))){
            HttpHost host = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);
            httpClient = HttpClients.custom()
                    .setRoutePlanner(routePlanner)
                    .build();
        }else {
            String proxy = System.getenv("http_proxy");
            if (proxy == null || proxy.isEmpty()) {
                httpClient = HttpClients.createDefault();
            } else {
                String[] hostAndPort = proxy.split(":");
                HttpHost host = new HttpHost(hostAndPort[0], Integer.valueOf(hostAndPort[1]));
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);
                httpClient = HttpClients.custom()
                        .setRoutePlanner(routePlanner)
                        .build();
            }
        }

        return httpClient;
    }
}
