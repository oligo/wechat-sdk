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

package net.rogers1b.wechat.message.framework.filter;

import net.rogers1b.wechat.message.framework.router.RequestContext;

/**
 * 消息过滤器，全局起作用。
 *
 * Created by Rogers on 15-7-16.
 */
public interface Filter {
    /**
     * 调用Filter，处理消息
     *
     * <p>Filter采用职责链模式设计，声明Filter时，应将全局Filter放置于链条末端。</p>
     * <p>返回true表示可以继续处理，否则不需要继续处理</p>
     * @param context RequestContext
     * @return boolean
     */
    public boolean check(RequestContext context);

    /**
     * 设置下一个filter
     *
     * <p>此处传入的是Filter实例，因此Filter应该被设计成无状态的对象。</p>
     * @param successor 下一个Filter
     * @return Filter 返回下一个Filter
     */
    public Filter setSuccessor(Filter successor) throws Exception;

    public Filter getSuccessor();
}
