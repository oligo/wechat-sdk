package net.rogers1b.wechat.message.framework.handler;

import net.rogers1b.wechat.message.framework.router.RequestContext;

import java.util.concurrent.Callable;

/**
 * Created by Rogers on 15-7-16.
 */
public interface RequestHandler<V> {
    public void setContext(RequestContext context);
    public RequestContext context();
    public Callable<V> getCallable();

    public void registerInterceptors(Interceptor<V> interceptor);

}
