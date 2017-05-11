package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 容器节点
 * @author qiuzhenhao
 * @date 2017/5/7
 */
public abstract class ContainerElement extends ContentElement{

    /* 子节点列表 */
    protected List<Element> childElements = new ArrayList<>();

    /* for-class定义的类型 */
    @Getter
    @Setter
    protected Class<?> forClass;

    /* 忽略的属性名称列表 */
    protected List<String> ignorePropertyNames = new ArrayList<>();

    public ContainerElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    /**
     * 增加子节点
     * @param element 子节点
     */
    public void addChildElement(Element element) {
        childElements.add(element);
    }

    /**
     * 增加忽略属性
     * @param propertyName 属性名称
     */
    public void addIgnoreProperty(String propertyName) {
        ignorePropertyNames.add(propertyName);
    }

    /**
     * 加载for-class的全部属性
     */
    public void loadForClassAllProperty() {
        if (forClass != null) {
            Field[] fields = forClass.getDeclaredFields();
            for (Field field : fields) {
                if (!ignorePropertyNames.contains(field.getName())) {
                    DataDefinition dataDefinition = new DataDefinition(field.getName());
                    PropertyElement propertyElement = new NormalPropertyElement(configuration, this.getNamespace(), this.getTemplateId(), dataDefinition, null);
                    if (!childElements.contains(propertyElement)) {
                        addChildElement(propertyElement);
                    }
                }
            }
        }
    }

    /**
     * 复制来自另一个容器节点的所有子节点
     * @param otherContainerElement 另一个容器节点
     */
    public void copyChildElement(ContainerElement otherContainerElement) {
        this.childElements.addAll(otherContainerElement.childElements);
    }

    /**
     * 返回子节点列表的迭代器
     * @return 子节点列表的迭代器
     */
    public Iterator<Element> childElementIterator() {
        return childElements.iterator();
    }

    /**
     * 判断是否是空子节点
     * @return 判断结果
     */
    public boolean isChildElementEmpty() {
        return childElements.isEmpty();
    }

}
