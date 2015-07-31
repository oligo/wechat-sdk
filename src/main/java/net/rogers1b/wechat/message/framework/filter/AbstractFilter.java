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
