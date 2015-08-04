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

package com.github.oligo.wechat.api.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.oligo.wechat.api.WeiXinApiInvoker;
import com.github.oligo.wechat.exception.WeiXinApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rogers
 */
public class UserGroup {
    private long id;
    private String name;
    private int count;

    public UserGroup() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    private static final String api = "https://api.weixin.qq.com/cgi-bin/groups/get";

    private static String getUrl(String accessToken){
        return api + "?access_token=" + accessToken;
    }

    public static List<UserGroup> build(String accessToken) throws WeiXinApiException{
        List<UserGroup> groups = new ArrayList<>();
        JsonNode jsonNode;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonNode = invoker.doRequest(getUrl(accessToken));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        ArrayNode node = (ArrayNode) jsonNode.get("groups");
        for(JsonNode n: node){
            n = (ObjectNode) n;
            UserGroup group = new UserGroup();
            group.setId(n.get("id").asLong());
            group.setName(n.get("name").asText());
            group.setCount(n.get("count").asInt());

            groups.add(group);
        }

        return groups;
    }
}
