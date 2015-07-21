/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rogers1b.wechat.api.media;

import net.rogers1b.wechat.api.Utils;
import net.rogers1b.wechat.exception.WeiXinApiException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String basePath;
    
    public MediaDownload(String accessToken, String mediaId, String basePath) {
        this.accessToken = accessToken;
        this.mediaId = mediaId;
        this.basePath = basePath.endsWith("/") ? basePath : basePath + "/";
    }

    
    public String getUrl(){
        return String.format(url, accessToken, mediaId);
    }
    
    public String doRequest() throws IOException, WeiXinApiException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getUrl());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            return saveFile(response);
        }catch (Exception e){
            logger.error("Downloading file failed: ", e);
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }
    
    private String saveFile(HttpResponse response) throws Exception{
        HttpEntity entity = response.getEntity();
        Header contentType = entity.getContentType();
        String fileName = Utils.randomString(7) + "." + contentType.getValue().split("/")[1];

        BufferedInputStream bis = new BufferedInputStream(entity.getContent());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this.basePath + fileName));
        
        int inByte;
        while(-1 != (inByte = bis.read())){
            bos.write(inByte);
        }
        
        bis.close();
        bos.close();
        
        return fileName;
    }
}
