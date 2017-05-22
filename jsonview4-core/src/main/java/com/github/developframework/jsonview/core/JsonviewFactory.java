package com.github.developframework.jsonview.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.developframework.jsonview.core.xml.ConfigurationSource;
import com.github.developframework.jsonview.core.xml.FileConfigurationSource;
import com.github.developframework.jsonview.core.xml.JsonviewConfigurationSaxReader;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Jsonview 工厂
 * @author qiuzhenhao
 */
public class JsonviewFactory {

    @Getter
    private JsonviewConfiguration jsonviewConfiguration;

    public JsonviewFactory(String... configs) {
        this(new ObjectMapper(), configs);
    }

    public JsonviewFactory(ObjectMapper objectMapper, String... configs) {
        Objects.requireNonNull(objectMapper);
        Objects.requireNonNull(configs);
        Set<ConfigurationSource> sources = new HashSet<>();
        for (String config : configs) {
            sources.add(new FileConfigurationSource(config));
        }
        JsonviewConfigurationSaxReader reader = new JsonviewConfigurationSaxReader(sources);
        jsonviewConfiguration = reader.readConfiguration();
        jsonviewConfiguration.setObjectMapper(objectMapper);
    }

    public JsonviewFactory(ObjectMapper objectMapper, Set<String> configs) {
        Objects.requireNonNull(objectMapper);
        Objects.requireNonNull(configs);
        Set<ConfigurationSource> sources = new HashSet<>();
        for (String config : configs) {
            sources.add(new FileConfigurationSource(config));
        }
        JsonviewConfigurationSaxReader reader = new JsonviewConfigurationSaxReader(sources);
        jsonviewConfiguration = reader.readConfiguration();
        jsonviewConfiguration.setObjectMapper(objectMapper);
    }

    public JsonviewFactory(ObjectMapper objectMapper, JsonviewConfiguration jsonviewConfiguration) {
        Objects.requireNonNull(jsonviewConfiguration);
        Objects.requireNonNull(objectMapper);
        jsonviewConfiguration.setObjectMapper(objectMapper);
        this.jsonviewConfiguration = jsonviewConfiguration;
    }

    /**
     * 返回ObjectMapper
     * @return ObjectMapper
     */
    public ObjectMapper getObjectMapper() {
        return jsonviewConfiguration.getObjectMapper();
    }

    /**
     * 获得Json生成器
     * @return Json生成器
     */
    public JsonProducer getJsonProducer() {
        return new DefaultJsonProducer(jsonviewConfiguration);
    }

    /**
     * 设置propertyNamingStrategy
     * @param propertyNamingStrategy propertyNamingStrategy
     */
    public void setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        jsonviewConfiguration.getObjectMapper().setPropertyNamingStrategy(propertyNamingStrategy);
    }

}
