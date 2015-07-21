package net.rogers1b.wechat.message.framework.router;

import net.rogers1b.wechat.message.framework.MessageType;

/**
 * Created by Rogers on 15-7-20.
 */
public interface Rule {
    public String asString();
    public boolean matchWith(MessageType type);
}
