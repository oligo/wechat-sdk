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

package com.github.oligo.wechat.api.menu;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 微信菜单按钮
 *
 * 暂时只处理简单的按钮类型
 *
 * Created by Rogers on 15-8-11.
 */
public class Button {
    private String name;
    private BtnType type;
    // key or url
    private String key;
    private String url;

    /**
     * 构造Click类型的菜单
     * @param name 菜单按钮名字
     * @param type 菜单类型
     * @param key 菜单Key
     */
    private Button(String name, BtnType type, String key) {
        this.name = name;
        this.type = type;
        this.key = assertSize(key, 128);
    }

    /**
     * 构造view类型的菜单
     * @param type
     * @param name
     * @param url
     */
    private Button(BtnType type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = assertSize(validateUrl(url), 256);
    }

    public static Button createClickButton(String name, BtnType type, String key){
        return new Button(name, type, key);
    }

    public static Button createViewButton(String name, String url){
        return new Button(name, BtnType.VIEW, url);
    }


    private String validateUrl(String url){
        try {
            URL u = new URL(url);
            URI uri = u.toURI();
            return uri.toString();
        }catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Not a valid Url");
        }

    }

    public String getName() {
        return name;
    }

    public BtnType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    private String assertSize(String field, int size){
        if(field.getBytes(Charset.defaultCharset()).length > size){
            throw new IllegalStateException("max size of field " + field + " exceeded");
        }

        return field;
    }
}
