package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.IfElement;
import org.xml.sax.Attributes;

/**
 * if节点解析器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
class IfElementSaxParser extends AbstractElementSaxParser{

    IfElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "if";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String condition = attributes.getValue("condition").trim();
        final IfElement ifElement = new IfElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), condition);
        parseContext.setCurrentIfElement(ifElement);
        addChildElement(parseContext, ifElement);
        parseContext.getStack().push(ifElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        parseContext.getStack().pop();
    }
}
