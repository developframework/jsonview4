package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ElseElement;
import org.xml.sax.Attributes;

/**
 * else节点解析器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
class ElseElementSaxParser extends AbstractElementSaxParser{

    ElseElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "else";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final ElseElement elseElement = new ElseElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId());
        parseContext.getCurrentIfElement().setElseElement(elseElement);
        parseContext.getStack().push(elseElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        parseContext.getStack().pop();
    }
}
