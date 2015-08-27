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

/**
 * 微信菜单按钮类型. 部分不常用的类型未包含在内
 *
 * Created by Rogers on 15-8-12.
 */
public enum BtnType {
    CLICK("click"), VIEW("view"), SCAN_CODE_PUSH("scancode_push"), SCAN_CODE_MSG("scancode_msg");

    private String name;

    private BtnType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BtnType fromName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Empty type name");
        }

        for(BtnType t: BtnType.values()){
            if(name.equals(t.getName())){
                return t;
            }
        }

        throw new IllegalArgumentException("Invalid type name");
    }
}
