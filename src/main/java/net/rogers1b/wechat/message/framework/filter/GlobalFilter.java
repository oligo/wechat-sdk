package net.rogers1b.wechat.message.framework.filter;

import net.rogers1b.wechat.message.framework.router.RequestContext;
import net.rogers1b.wechat.message.framework.router.Rules;

/**
 * Created by Rogers on 15-7-20.
 */
public class GlobalFilter implements Filter {

    /**
     * 可以继承此类，重写此方法，定制处理方式。
     * @param context RequestContext
     * @return
     */
    @Override
    public boolean check(RequestContext context){
        return true;
    }

    /**
     * 全局过滤器应为最后一个过滤器
     *
     * @param successor
     */
    @Override
    public Filter setSuccessor(Filter successor) throws Exception{
        throw new IllegalAccessException("GlobalFilter#setSuccesor不能被调用");
    }

    public Filter getSuccessor(){
        return null;
    }
}
