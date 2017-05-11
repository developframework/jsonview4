package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.ObjectElement;
import org.xml.sax.Attributes;

/**
 * 对象节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class ObjectElementSaxParser extends ContainerElementSaxParser<ObjectElement>{


    ObjectElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "object";
    }

    @Override
    protected ObjectElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new ObjectElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(ObjectElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        handleForClass(element, attributes);
    }
}
