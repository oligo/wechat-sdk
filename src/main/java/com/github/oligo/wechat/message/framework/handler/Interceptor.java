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

package com.github.oligo.wechat.message.framework.handler;

import com.github.oligo.wechat.message.framework.router.RequestContext;

/**
 * 消息拦截器(处理单元)，注册在Handler上, 采用链式处理。
 *
 * Created by Rogers on 15-7-16.
 */
public interface Interceptor<V> {
    /**
     * 调用Interceptor，处理消息, 勿直接调用此方法。
     *
     * @param context RequestContext
     * @return V return value of handler
     */
    public V doWork(RequestContext context);

    public V start(RequestContext context);
    /**
     * 设置下一个Interceptor
     *
     * <p>此处传入的是Interceptor实例，因此Interceptor应该被设计成无状态的对象。</p>
     * @param successor next interceptor
     * @return Interceptor 返回successor
     */
    public Interceptor<V> setSuccessor(Interceptor<V> successor);

    public Interceptor<V> getSuccessor();
}
