package net.rogers1b.wechat.message.framework.filter;

import net.rogers1b.wechat.message.framework.router.RequestContext;
import net.rogers1b.wechat.message.framework.router.Rules;

/**
 * Created by Rogers on 15-7-20.
 */
public abstract class AbstractFilter implements Filter {
    protected Filter successor;

    /**
     * 可以继承此类，重写此方法，定制处理方式。
     * @param context RequestContext
     * @return
     */
    @Override
    public abstract boolean check(RequestContext context);

    /**
     * 全局过滤器应为最后一个过滤器
     *
     * @param successor
     */
    @Override
    public Filter setSuccessor(Filter successor) throws Exception{
        if(null == successor){
            throw new IllegalArgumentException("Filter is null!");
        }
        this.successor = successor;
        return this.successor;
    }

    public Filter getSuccessor(){
        return this.successor;
    }
}
