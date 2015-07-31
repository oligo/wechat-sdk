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
            Interceptor<String> current = this.interceptor;
            while (current.getSuccessor() != null) {
                current = current.getSuccessor();
            }
            current.setSuccessor(interceptor);
        }

    }
}
