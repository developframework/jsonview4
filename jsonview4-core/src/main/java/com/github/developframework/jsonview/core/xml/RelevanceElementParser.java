package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.RelevanceElement;
import org.xml.sax.Attributes;

/**
 * 关联节点解析器
 * @author qiuzhenhao
 */
class RelevanceElementParser extends AbstractElementSaxParser {


    public RelevanceElementParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "relevance";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String data = attributes.getValue("data").trim();
        final String alias = attributes.getValue("alias");
        final String relFunctionValue = attributes.getValue("rel-function");
        final String mapFunctionValue = attributes.getValue("map-function");
        final RelevanceElement relevanceElement = new RelevanceElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), new DataDefinition(data), alias, relFunctionValue);
        relevanceElement.setMapFunctionValue(mapFunctionValue);
        relevanceElement.setRelFunctionValue(relFunctionValue);
        addChildElement(parseContext, relevanceElement);
        parseContext.getStack().push(relevanceElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        parseContext.getStack().pop();
    }
}
