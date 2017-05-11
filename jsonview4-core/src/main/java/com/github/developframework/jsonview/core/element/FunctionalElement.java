package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 功能型节点
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public abstract class FunctionalElement extends Element{

    /* 子节点列表 */
    protected List<Element> childElements = new LinkedList<>();

    public FunctionalElement(JsonviewConfiguration configuration, String namespace, String templateId) {
        super(configuration, namespace, templateId);
    }

    /**
     * 增加子节点
     * @param element 子节点
     */
    public void addChildElement(Element element) {
        childElements.add(element);
    }

    /**
     * 返回子节点列表的迭代器
     * @return 子节点列表的迭代器
     */
    public Iterator<Element> childElementIterator() {
        return childElements.iterator();
    }
}
