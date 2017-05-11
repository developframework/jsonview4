package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.ContainerElement;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.FunctionalElement;

/**
 * 抽象的xml解析器
 * @author qiuzhenhao
 * @date 2017/5/7
 */
abstract class AbstractElementSaxParser implements ElementSaxParser{

    protected JsonviewConfiguration jsonviewConfiguration;

    public AbstractElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        this.jsonviewConfiguration = jsonviewConfiguration;
    }

    protected void addChildElement(ParseContext parseContext, Element element) {
        Element parentElement = parseContext.getStack().peek();
        if(parentElement instanceof ContainerElement) {
            ((ContainerElement) parentElement).addChildElement(element);
        } if(parentElement instanceof FunctionalElement) {
            ((FunctionalElement) parentElement).addChildElement(element);
        }
    }
}
