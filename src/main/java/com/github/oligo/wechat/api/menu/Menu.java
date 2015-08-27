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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

/**
 * 微信菜单管理
 *
 * Created by Rogers on 15-8-11.
 */
public class Menu {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(Menu.class);

    private static String apiTpl = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
//    private SortedSet<Button> btns;

    public void createFromFile(String accessToken, File jsonFile) {
        try {
            JsonNode root = loadJsonFile(jsonFile);
            JsonNode buttons = root.get("button");
            if( !buttons.isArray()){
                throw new RuntimeException("Invalid menu structure");
            }

            String api = String.format(apiTpl, accessToken);


        }catch (JsonParseException |JsonMappingException e){
            throw new RuntimeException("Invalid json file");
        }
    }

    public void createFromJson(){

    }

    public JsonNode loadJsonFile(File jsonFile) throws JsonParseException,
            JsonMappingException{
        try {
            return objectMapper.readValue(jsonFile, JsonNode.class);
        }catch (IOException e){
            logger.error("Closing Json parser failed!");
            throw new RuntimeException(e);
        }
    }
}
