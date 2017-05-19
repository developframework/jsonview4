package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ExtendPortElement;
import org.xml.sax.Attributes;

/**
 * 扩展端口节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class ExtendPortElementSaxParser extends AbstractElementSaxParser {

    public ExtendPortElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "extend-port";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String portName = attributes.getValue("port-name");
        final ExtendPortElement extendPortElement = new ExtendPortElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), portName);
        addChildElement(parseContext, extendPortElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        // 无操作
    }
}
