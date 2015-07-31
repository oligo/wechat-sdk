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
