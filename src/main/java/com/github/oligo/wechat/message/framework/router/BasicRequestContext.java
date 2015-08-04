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

package com.github.oligo.wechat.message.framework.router;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理的上下文
 *
 * rule被设计成与框架无关，具体的路由路径与实现相关
 *
 * Created by Rogers on 15-7-16.
 */
public abstract class BasicRequestContext implements RequestContext {
    protected long creationTime;
    // 存放请求的元数据
    protected Map<String, Object> attributes = new HashMap<>();

    protected Rule rule;

    //原始数据
    protected InputStream payload;
    // 解析后的数据
    protected Map<String, Object> dataMap = new HashMap<>();

    /**
     * 从微信请求的XML数据流构造出RequestContext
     *
     * @param payload http input stream
     */
    public BasicRequestContext(InputStream payload) {
        this.creationTime = System.currentTimeMillis();
        this.payload = payload;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public Map<String, Object> getParsedData() {
        return dataMap;
    }

    @Override
    public Rule currentRule() {
        return rule;
    }

    @Override
    public abstract RequestContext build();

    public abstract void parsePayload() throws Exception;

}
