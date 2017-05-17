package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 容器功能节点
 * @author qiuzhenhao
 */
public abstract class ContainerFunctionalElement extends FunctionalElement implements ContainChildElementable{

    /* 子节点列表 */
    protected List<Element> childElements = new LinkedList<>();

    public ContainerFunctionalElement(JsonviewConfiguration configuration, String namespace, String templateId) {
        super(configuration, namespace, templateId);
    }

    @Override
    public void addChildElement(Element element) {
        childElements.add(element);
    }

    @Override
    public void copyChildElement(ContainChildElementable otherContainer) {
        this.childElements.addAll(otherContainer.getChildElements());
    }

    @Override
    public final Iterator<Element> childElementIterator() {
        return childElements.iterator();
    }

    @Override
    public final List<Element> getChildElements() {
        return childElements;
    }

    @Override
    public final boolean isChildElementEmpty() {
        return childElements.isEmpty();
    }
}
