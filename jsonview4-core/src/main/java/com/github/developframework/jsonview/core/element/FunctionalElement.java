package com.github.developframework.jsonview.core.element;

import com.github.developframework.jsonview.core.JsonviewConfiguration;

/**
 * 功能型节点
 *
 * @author qiuzhenhao
 */
public abstract class FunctionalElement extends Element {

    public FunctionalElement(JsonviewConfiguration configuration, String namespace, String templateId) {
        super(configuration, namespace, templateId);
    }
}
