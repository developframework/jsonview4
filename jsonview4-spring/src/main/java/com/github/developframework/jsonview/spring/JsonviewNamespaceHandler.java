package com.github.developframework.jsonview.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * spring注册jsonview命名空间的Handler
 *
 * @author qiuzhenhao
 */
public class JsonviewNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("scan", new JsonviewScanBeanDefinitionParser());
    }
}
