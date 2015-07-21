package net.rogers1b.wechat.message.framework.router;

import java.io.InputStream;
import java.util.Map;

/**
 * 请求上下文
 *
 * 存储请求的元数据与业务数据, 此对象将被用作请求数据的主要载体。
 *
 * Created by Rogers on 15-7-16.
 */
public interface RequestContext {
    public boolean isEmpty();

    public long getCreationTime();

    public Object getAttribute(String key);

    public void setAttribute(String key, Object value);

    public Map<String, Object> getParsedData();

    public void buildRule();

    public Rule currentRule();
}
