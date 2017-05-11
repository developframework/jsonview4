package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ContainerElement;
import org.xml.sax.Attributes;

/**
 * 忽略的属性节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class IgnorePropertyElementSaxParser extends AbstractElementSaxParser{

    IgnorePropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property-ignore";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String name = attributes.getValue("name").trim();
        ((ContainerElement) parseContext.getStack().peek()).addIgnoreProperty(name);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        // 无操作
    }
}
