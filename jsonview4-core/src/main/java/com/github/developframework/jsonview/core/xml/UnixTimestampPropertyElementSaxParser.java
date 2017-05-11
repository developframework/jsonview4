package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.PropertyElement;
import com.github.developframework.jsonview.core.element.UnixTimestampPropertyElement;

/**
 * unix时间戳型属性节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class UnixTimestampPropertyElementSaxParser extends PropertyElementSaxParser {

    UnixTimestampPropertyElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "property-unixtimestamp";
    }

    @Override
    protected PropertyElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new UnixTimestampPropertyElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }
}
