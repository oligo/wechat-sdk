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

import java.util.Map;

/**
 * 请求上下文
 *
 * 存储请求的元数据与业务数据, 此对象将被用作请求数据的主要载体。
 *
 * Created by Rogers on 15-7-16.
 */
public interface RequestContext {
    public boolean isEmpty();

    public long getCreationTime();

    public Object getAttribute(String key);

    public void setAttribute(String key, Object value);

    public Map<String, Object> getParsedData();

    public RequestContext build();

    public Rule currentRule();
}
