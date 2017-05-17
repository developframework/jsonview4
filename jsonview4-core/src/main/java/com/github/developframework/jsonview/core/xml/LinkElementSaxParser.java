package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.LinkElement;
import org.xml.sax.Attributes;

/**
 * 一对一链接节点解析器
 *
 * @author qiuzhenhao
 */
public class LinkElementSaxParser extends AbstractElementSaxParser  {

    public LinkElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "link";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String data = attributes.getValue("data").trim();
        final String alias = attributes.getValue("alias");
        final LinkElement linkElement = new LinkElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), new DataDefinition(data), alias);
        addChildElement(parseContext, linkElement);
        parseContext.getStack().push(linkElement);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        parseContext.getStack().pop();
    }
}
