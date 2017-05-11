package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import com.github.developframework.jsonview.core.element.NormalPropertyElement;
import com.github.developframework.jsonview.core.element.PropertyElement;
import org.xml.sax.Attributes;

/**
 * 属性节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class PropertyElementSaxParser extends ContentElementSaxParser<PropertyElement>{

    public PropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property";
    }

    @Override
    protected PropertyElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new NormalPropertyElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(PropertyElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        element.setConverterValue(attributes.getValue("converter"));
    }

    @Override
    protected void otherOperation(ParseContext parseContext, PropertyElement element) {
        // 无操作
    }
}
