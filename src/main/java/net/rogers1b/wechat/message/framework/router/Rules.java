package net.rogers1b.wechat.message.framework.router;

import net.rogers1b.wechat.message.framework.MessageType;

import java.lang.annotation.*;

/**
 * value: java Regex pattern string
 *
 * Created by Rogers on 15-7-16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rules {
    MessageType value();
}
