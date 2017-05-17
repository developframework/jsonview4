package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.LinkElement;
import com.github.developframework.jsonview.core.element.ObjectElement;
import com.github.developframework.jsonview.core.exception.JsonviewParseXmlException;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;

/**
 * 一对一链接节点解析器
 *
 * @author qiuzhenhao
 */
public class LinkElementSaxParser extends ContainerElementSaxParser<LinkElement>  {

    public LinkElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "link";
    }

    @Override
    protected LinkElement createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        return new LinkElement(jsonviewConfiguration, parseContext.getCurrentTemplate().getNamespace(), parseContext.getCurrentTemplate().getTemplateId(), dataDefinition, alias);
    }

    @Override
    protected void addOtherAttributes(LinkElement element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        handleForClass(element, attributes);
    }
}
