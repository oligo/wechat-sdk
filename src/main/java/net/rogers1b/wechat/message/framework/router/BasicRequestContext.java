package net.rogers1b.wechat.message.framework.router;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理的上下文
 *
 * rule被设计成与框架无关，具体的路由路径与实现相关
 *
 * Created by Rogers on 15-7-16.
 */
public abstract class BasicRequestContext implements RequestContext {
    protected long creationTime;
    // 存放请求的元数据
    protected Map<String, Object> attributes = new HashMap<>();

    protected Rule rule;

    //原始数据
    protected InputStream payload;
    // 解析后的数据
    protected Map<String, Object> dataMap = new HashMap<>();

    public BasicRequestContext(InputStream payload) {
        this.creationTime = System.currentTimeMillis();
        this.payload = payload;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public Map<String, Object> getParsedData() {
        return dataMap;
    }

    @Override
    public Rule currentRule() {
        return rule;
    }

    @Override
    public abstract RequestContext build();

    public abstract void parsePayload() throws Exception;

}
