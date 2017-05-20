package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.DatePropertyElement;
import com.github.developframework.jsonview.core.element.PropertyElement;
import org.xml.sax.Attributes;

/**
 * 日期时间类型属性节点解析器
 * @author qiuzhenhao
 */
class DatePropertyElementSaxParser extends PropertyElementSaxParser {

    DatePropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property-date";
    }

    @Override
    protected PropertyElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new DatePropertyElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(PropertyElement element, Attributes attributes) {
        super.addOtherAttributes(element, attributes);
        ((DatePropertyElement)element).setPattern(attributes.getValue("pattern"));
    }
}
