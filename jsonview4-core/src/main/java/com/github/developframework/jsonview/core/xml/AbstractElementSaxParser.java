package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ContainChildElementable;
import com.github.developframework.jsonview.core.element.Element;

/**
 * 抽象的xml解析器
 *
 * @author qiuzhenhao
 */
abstract class AbstractElementSaxParser implements ElementSaxParser{

    protected JsonviewConfiguration jsonviewConfiguration;

    public AbstractElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        this.jsonviewConfiguration = jsonviewConfiguration;
    }

    protected void addChildElement(ParseContext parseContext, Element element) {
        Element parentElement = parseContext.getStack().peek();
        if(parentElement instanceof ContainChildElementable) {
            ((ContainChildElementable) parentElement).addChildElement(element);
        }
    }
}
