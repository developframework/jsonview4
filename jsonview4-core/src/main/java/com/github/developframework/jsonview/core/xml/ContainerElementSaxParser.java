package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ContainerElement;
import com.github.developframework.jsonview.core.exception.JsonviewParseXmlException;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;

/**
 * 容器节点解析器
 * @author qiuzhenhao
 * @date 2017/5/7
 * @param <T> 容器节点类型
 */
abstract class ContainerElementSaxParser<T extends ContainerElement> extends ContentElementSaxParser<T> {

    public ContainerElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        ((ContainerElement) parseContext.getStack().pop()).loadForClassAllProperty();
    }

    @Override
    protected void addOtherAttributes(T element, Attributes attributes) {
        element.setNullHidden(attributes.getValue("null-hidden"));
        element.setForClass(attributes.getValue("for-class"));
    }

    @Override
    protected void otherOperation(ParseContext parseContext, T element) {
        parseContext.getStack().push(element);
    }
}
