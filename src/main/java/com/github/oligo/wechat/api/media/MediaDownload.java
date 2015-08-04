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

package com.github.oligo.wechat.api.media;

import com.github.oligo.wechat.exception.WeiXinApiException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rogers
 */
public class MediaDownload {
    private static final Logger logger = LoggerFactory.getLogger(MediaDownload.class);
    private static String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    private String accessToken;
    private String mediaId;

    public MediaDownload(String accessToken, String mediaId) {
        this.accessToken = accessToken;
        this.mediaId = mediaId;
    }

    
    public String getUrl(){
        return String.format(url, accessToken, mediaId);
    }
    
    public HttpResponse doRequest() throws IOException, WeiXinApiException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getUrl());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            return response;
        }catch (Exception e){
            logger.error("Downloading file failed: ", e);
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }
    
    public String saveToLocalDisk(HttpResponse response, String basePath) throws Exception{
        basePath = basePath.endsWith("/") ? basePath : basePath + "/";
        HttpEntity entity = response.getEntity();
        Header header = response.getFirstHeader("Content-Disposition");
        String filename = parseContentDisposition(header.getValue());
        BufferedInputStream bis = new BufferedInputStream(entity.getContent());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(basePath + filename));
        
        int inByte;
        while(-1 != (inByte = bis.read())){
            bos.write(inByte);
        }
        
        bis.close();
        bos.close();
        
        return filename;
    }

    public static String getFilename(HttpResponse response){
        Header header = response.getFirstHeader("Content-Disposition");
        return parseContentDisposition(header.getValue());
    }

    private static final Pattern CONTENT_DISPOSITION_PATTERN =
            Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");

    private static String parseContentDisposition(String contentDisposition){
        try {
            Matcher m = CONTENT_DISPOSITION_PATTERN.matcher(contentDisposition);
            if (m.find()) {
                return m.group(1);
            }
        } catch (IllegalStateException ex) {
            return null;
        }
        return null;
    }
}
