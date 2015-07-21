package net.rogers1b.wechat.message.wxHandler;

import net.rogers1b.wechat.message.framework.handler.Interceptor;
import net.rogers1b.wechat.message.framework.router.RequestContext;

/**
 * Created by Rogers on 15-7-21.
 */
public abstract class AbstractInterceptor implements Interceptor<String> {
    protected Interceptor<String> successor;

    @Override
    public abstract String doWork(RequestContext context);

    public Interceptor<String> setSuccessor(Interceptor<String> successor) throws Exception{
        this.successor = successor;
        return this.successor;
    }

    public Interceptor<String> getSuccessor(){
        return this.successor;
    }
}
