package net.rogers1b.wechat.message;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import net.rogers1b.wechat.message.framework.router.BasicRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

/**
 * Created by Rogers on 15-7-16.
 */
public class WxRequestContext extends BasicRequestContext {
    private static final Logger logger = LoggerFactory.getLogger(WxRequestContext.class);
    private WxRule rule;

    public WxRequestContext(InputStream payload) {
        super(payload);
        logger.debug("Request Context created: " + creationTime);
    }

    @Override
    public void buildRule(){
        try {
            parsePayload();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        if(dataMap.get("MsgType").equals("event")){
            rule = new WxRule((String)dataMap.get("MsgType"), (String) dataMap.get("FromUserName"), Long.parseLong((String)dataMap.get("CreateTime")));
        }else{
            rule = new WxRule((String)dataMap.get("MsgType"), (String) dataMap.get("FromUserName"), (long)dataMap.get("MsgId"));
        }
    }

    /**
     * 解析微信消息XML，只能处理无嵌套的扁平结构, 否则抛出异常
     * @throws Exception
     */
    @Override
    public void parsePayload() throws Exception{
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(payload);

        String currentElement = null;
        while (reader.hasNext()){
            int eventType = reader.next();
            switch (eventType){
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getName().getLocalPart();
                    if(Strings.isNullOrEmpty(elementName) || elementName.equals("xml")){
                        break;
                    }
                    currentElement = elementName;
                    logger.debug(currentElement);
                    dataMap.putIfAbsent(currentElement, reader.getElementText());
                    break;

                case XMLStreamReader.END_DOCUMENT:
                    logger.debug("文档处理结束");
                    break;
            }
        }

        logger.debug(dataMap.toString());
        reader.close();
        validateData();
    }

    private void validateData(){
        Preconditions.checkNotNull(dataMap.containsKey("ToUserName"), "Xml缺少ToUserName元素");
        Preconditions.checkNotNull(dataMap.containsKey("FromUserName"), "Xml缺少FromUserName元素");
        Preconditions.checkNotNull(dataMap.containsKey("CreateTime"), "Xml缺少CreateTime元素");
        Preconditions.checkNotNull(dataMap.containsKey("MsgType"), "Xml缺少MsgType元素");
    }
}