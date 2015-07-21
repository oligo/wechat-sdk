package net.rogers1b.wechat.message;

import net.rogers1b.wechat.message.framework.MessageType;
import net.rogers1b.wechat.message.framework.router.Rule;

/**
 * Created by Rogers on 15-7-20.
 */
public class WxRule implements Rule {
    private String type;
    private String user;
    private String msgId;
    private long createTime;

    public WxRule(String type, String user, String msgId) {
        this.type = type;
        this.user = user;
        this.msgId = msgId;
    }

    public WxRule(String type, String user, long createTime) {
        this.type = type;
        this.user = user;
        this.createTime = createTime;
    }

    @Override
    public String asString(){
        if(type.equals("event")){
            return "/type/" + type + "/user/" + user + "/time/" + createTime;
        }else{
            return "/type/" + type + "/user/" + user + "/msgid" + msgId;
        }
    }

    @Override
    public boolean matchWith(MessageType type1){
        return type1.equals(MessageType.valueOf(type.toUpperCase()));
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getMsgId() {
        return msgId;
    }

    public long getCreateTime() {
        return createTime;
    }
}
