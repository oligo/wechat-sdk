package net.rogers1b.wechat.message.framework.handler;

import net.rogers1b.wechat.message.framework.router.RequestContext;

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
     * @param successor
     * @return Interceptor 返回successor
     */
    public Interceptor<V> setSuccessor(Interceptor<V> successor);

    public Interceptor<V> getSuccessor();
}
