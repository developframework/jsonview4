package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.RelevanceElement;
import org.xml.sax.Attributes;

/**
 * 关联节点解析器
 * @author qiuzhenhao
 */
class RelevanceElementParser extends ContainerElementSaxParser<RelevanceElement> {


    public RelevanceElementParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "relevance";
    }

    @Override
    protected RelevanceElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new RelevanceElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(RelevanceElement element, Attributes attributes) {
        super.addOtherAttributes(element, attributes);
        element.setRelFunctionValue(attributes.getValue("rel-function"));
        element.setMapFunctionValue(attributes.getValue("map-function"));
        element.setRelevanceType(attributes.getValue("type"));
    }
}
