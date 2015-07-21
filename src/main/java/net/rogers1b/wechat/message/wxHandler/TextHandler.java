package net.rogers1b.wechat.message.wxHandler;

import net.rogers1b.wechat.message.framework.MessageType;
import net.rogers1b.wechat.message.framework.router.Rules;

import java.util.concurrent.Callable;

/**
 * Created by Rogers on 15-7-20.
 */
@Rules(MessageType.TEXT)
public class TextHandler extends AbstractWxHandler{
}
