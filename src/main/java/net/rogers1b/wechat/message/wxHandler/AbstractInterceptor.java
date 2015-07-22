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

    @Override
    public Interceptor<String> setSuccessor(Interceptor<String> successor){
        this.successor = successor;
        return this.successor;
    }

    @Override
    public Interceptor<String> getSuccessor() {
        return this.successor;
    }

    @Override
    public String start(RequestContext context) {
        String result = doWork(context);
        if (null == result) {
            throw new RuntimeException("Interceptor#doWork should not return null");
        }

        if (!result.isEmpty()) {
            return result;
        } else if (this.getSuccessor() != null) {
            return this.getSuccessor().doWork(context);
        } else {
            // 不能处理此事件。
            return "";
        }
    }
}
