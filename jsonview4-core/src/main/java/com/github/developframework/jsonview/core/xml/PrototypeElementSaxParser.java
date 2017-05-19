package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.PrototypeElement;

/**
 * 原型节点解析器
 * @author qiuzhenhao
 */
class PrototypeElementSaxParser extends PropertyElementSaxParser{

    PrototypeElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "prototype";
    }

    @Override
    protected PrototypeElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new PrototypeElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

}
