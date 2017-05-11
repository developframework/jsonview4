package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.ArrayElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import org.xml.sax.Attributes;

/**
 * 数组节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class ArrayElementSaxParser extends ContainerElementSaxParser<ArrayElement> {

    ArrayElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "array";
    }

    @Override
    protected ArrayElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new ArrayElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(ArrayElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        element.setMapFunctionValue(attributes.getValue("map-function"));
        handleForClass(element, attributes);
    }
}
