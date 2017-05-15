package com.github.developframework.jsonview.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.developframework.jsonview.core.JsonviewFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * JsonviewFactory 的 FactoryBean
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public class JsonviewFactoryFactoryBean implements FactoryBean<JsonviewFactory>  {

    @Getter
    @Setter
    private Set<String> configs;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JsonviewFactory getObject() throws Exception {
        return new JsonviewFactory(objectMapper, configs);
    }

    @Override
    public Class<?> getObjectType() {
        return JsonviewFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
