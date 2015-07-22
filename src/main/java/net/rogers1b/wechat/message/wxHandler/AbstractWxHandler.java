package net.rogers1b.wechat.message.wxHandler;

import net.rogers1b.wechat.message.framework.MessageType;
import net.rogers1b.wechat.message.framework.handler.Interceptor;
import net.rogers1b.wechat.message.framework.handler.RequestHandler;
import net.rogers1b.wechat.message.framework.router.RequestContext;
import net.rogers1b.wechat.message.framework.router.Rules;

import java.util.concurrent.Callable;

/**
 * Created by Rogers on 15-7-20.
 */
//@Rules(MessageType.TEXT)
public abstract class AbstractWxHandler implements RequestHandler<String>{
    private RequestContext context;
    private Interceptor<String> interceptor;

    public void setContext(RequestContext context){
        this.context = context;
    }

    public RequestContext context(){
        return context;
    }


    public Callable<String> getCallable(){
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
//                WxRule rule = (WxRule) context.currentRule();
                return interceptor.start(context);
            }
        };
    }

    @Override
    public void registerInterceptors(Interceptor<String> interceptor){
        if(interceptor == null){
            throw new IllegalArgumentException("Interceptor is null!");
        }

        if(this.interceptor == null){
            this.interceptor = interceptor;
        }else{
            Interceptor<String> next = this.interceptor.getSuccessor();
            while (next != null) {
                next = next.getSuccessor();
                if(next == null) {
                    next = interceptor;
                }
            }
        }

    }
}
