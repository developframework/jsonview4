package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.BooleanPropertyElement;
import com.github.developframework.jsonview.core.element.PropertyElement;

/**
 * 布尔型属性节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class BooleanPropertyElementSaxParser extends PropertyElementSaxParser {


    BooleanPropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property-boolean";
    }

    @Override
    protected PropertyElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new BooleanPropertyElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }
}
