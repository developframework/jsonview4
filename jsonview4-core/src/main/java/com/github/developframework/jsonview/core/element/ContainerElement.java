package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.exception.JsonviewParseXmlException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 容器节点
 * @author qiuzhenhao
 */
public abstract class ContainerElement extends ContentElement implements ContainChildElementable{

    /* 子节点列表 */
    protected List<Element> childElements = new ArrayList<>();

    /* for-class定义的类型 */
    @Getter
    protected Class<?> forClass;

    /* 忽略的属性名称列表 */
    protected List<String> ignorePropertyNames = new ArrayList<>();

    public ContainerElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    /**
     * 设置for-class
     * @param className 类名
     */
    public void setForClass(String className) {
        if (StringUtils.isNotBlank(className)) {
            try {
                forClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonviewParseXmlException("Class \"%s\" is not found, please check configuration file.", className);
            }
        }
    }

    /**
     * 增加忽略属性
     * @param propertyName 属性名称
     */
    public final void addIgnoreProperty(String propertyName) {
        ignorePropertyNames.add(propertyName);
    }

    /**
     * 加载for-class的全部属性
     */
    public final void loadForClassAllProperty() {
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
    public final boolean isChildElementEmpty() {
        return childElements.isEmpty();
    }


    @Override
    public final List<Element> getChildElements() {
        return childElements;
    }
}
