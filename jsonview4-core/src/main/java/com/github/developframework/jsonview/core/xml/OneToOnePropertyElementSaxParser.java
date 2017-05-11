package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.OneToOnePropertyElement;
import org.xml.sax.Attributes;

/**
 * 一对一属性链接节点解析器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
class OneToOnePropertyElementSaxParser extends ContentElementSaxParser<OneToOnePropertyElement>{

    OneToOnePropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property-one-to-one";
    }

    @Override
    protected OneToOnePropertyElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new OneToOnePropertyElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(OneToOnePropertyElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        element.setConverterValue(attributes.getValue("converter"));
    }

    @Override
    protected void otherOperation(ParseContext parseContext, OneToOnePropertyElement element) {

    }
}
