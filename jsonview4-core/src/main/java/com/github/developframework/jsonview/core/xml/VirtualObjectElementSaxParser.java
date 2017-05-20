package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.VirtualObjectElement;
import org.xml.sax.Attributes;

/**
 * 虚拟对象节点解析器
 * @author qiuzhenhao
 */
class VirtualObjectElementSaxParser extends ContainerElementSaxParser<VirtualObjectElement>{


    VirtualObjectElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "object-virtual";
    }

    @Override
    protected VirtualObjectElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new VirtualObjectElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(VirtualObjectElement element, Attributes attributes) {
        // 无操作
    }
}
