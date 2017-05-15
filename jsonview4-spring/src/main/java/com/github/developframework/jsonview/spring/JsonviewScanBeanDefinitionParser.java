package com.github.developframework.jsonview.spring;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.JsonviewFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * JsonviewScan的spring bean 解析器
 *
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public class JsonviewScanBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return JsonviewFactory.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        final String locations = element.getAttribute("locations");
        final String objectMapperRef = element.getAttribute("object-mapper-ref");
        if (StringUtils.hasText(locations)) {
            JsonviewScanLoader loader = new JsonviewScanLoader(locations);
            JsonviewConfiguration jsonviewConfiguration = loader.createJsonviewConfiguration();
            builder.addConstructorArgValue(jsonviewConfiguration);
            if (StringUtils.hasText(objectMapperRef)) {
                builder.addConstructorArgReference(objectMapperRef);
            }
        }
    }
}
