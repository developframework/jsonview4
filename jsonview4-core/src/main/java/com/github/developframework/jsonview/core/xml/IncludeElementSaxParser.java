package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.IncludeElement;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;

/**
 * 包含节点解析器
 * @author qiuzhenhao
 */
class IncludeElementSaxParser extends AbstractElementSaxParser{

    IncludeElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "include";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String templateId = attributes.getValue("id").trim();
        String namespace = attributes.getValue("namespace");
        namespace = StringUtils.isNotBlank(namespace) ? namespace.trim() : parseContext.getCurrentTemplatePackage().getNamespace();
        final IncludeElement includeElement = new IncludeElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), namespace, templateId);
        addChildElement(parseContext, includeElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        // 无操作
    }
}
