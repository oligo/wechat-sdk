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

package net.rogers1b.wechat.message.framework.router;

import net.rogers1b.wechat.message.framework.handler.RequestHandler;
import net.rogers1b.wechat.message.framework.filter.Filter;

import java.util.List;
import java.util.Set;

/**
 * 消息路由器
 * <p>
 * 消息路由规则rule以类似URL的形式设计。
 * 路由器可以注册消息处理器和消息过滤器，消息处理器内可以注册拦截器，
 * 拦截器针对特定类型的消息进行拦截，并返回处理结果，本质上讲，拦截器是消息处理器内的实际处理单元。
 * </p>
 * Created by Rogers on 15-7-16.
 */
public interface Router<T> {
    /**
     * 注册消息处理器
     *
     * @param handlerClass RequestHandler class
     */
    public void registerHandler(RequestHandler<T> handlerClass);

    public List<RequestHandler> allHandlers();

    /**
     * 注册消息过滤器
     *
     * <p>Filter采用职责链设计模式，因此只需注册置顶的Filter即可。</p>
     *
     * @param filter Filter instance
     */
    public void registerFilter(Filter filter);

    public List<Filter> allFilters();

    /**
     * 将消息处理的任务委托于Handler
     *
     * @param context RequestContext
     * @return T
     */
    public T process(RequestContext context);

    /**
     *
     *  应由{@link RouterImpl#process }调用
     */
    public boolean adoptFilters(RequestContext context);

    /**
     * handler处理超时或异常, 或消息被Filter过滤时，由此方法做补救处理
     *
     * 应由{@link net.rogers1b.wechat.message.framework.router.Router#process}调用
     *
     * @return T
     */
    public T onError();

    /**
     * 根据路由规则，查询对应的消息处理器
     *
     * @param rule 类URL的路由规则
     * @return RequestHandler
     */
    public RequestHandler<T> findHandler(Rule rule);

    /**
     * 初始化路由系统
     * <p>
     * 初始化时完成的工作包括: 检查handlers，filters, rules, 设置 handler executors等。
     */
    public void initialize();

    /**
     *  关停消息路由系统
     */
    public void destroy();
}
