package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.OneToOneObjectElement;
import org.xml.sax.Attributes;

/**
 * 数组一对一拼接节点解析器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
class OneToOneObjectElementSaxParser extends ContainerElementSaxParser<OneToOneObjectElement> {


    OneToOneObjectElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "object-one-to-one";
    }

    @Override
    protected OneToOneObjectElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new OneToOneObjectElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(OneToOneObjectElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        handleForClass(element, attributes);
    }
}
