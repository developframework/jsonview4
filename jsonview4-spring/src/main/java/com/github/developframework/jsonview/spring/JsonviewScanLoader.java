package com.github.developframework.jsonview.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.JsonviewFactory;
import com.github.developframework.jsonview.core.exception.JsonviewException;
import com.github.developframework.jsonview.core.xml.ConfigurationSource;
import com.github.developframework.jsonview.core.xml.JsonviewConfigurationSaxReader;
import lombok.Getter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Jsonview的扫描加载器
 *
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public class JsonviewScanLoader {

    @Getter
    private String locations;

    public JsonviewScanLoader(String locations) {
        this.locations = locations;
    }

    /**
     * 创建jsonviewConfiguration
     *
     * @return jsonviewConfiguration
     */
    public JsonviewConfiguration createJsonviewConfiguration() {
        final String[] locationsArray = StringUtils.tokenizeToStringArray(locations, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            final Set<ConfigurationSource> sources = new HashSet<>();
            for (String locationOne : locationsArray) {
                final Resource[] resources = resolver.getResources(locationOne);
                for (int i = 0; i < resources.length; i++) {
                    sources.add(new SpringResourceConfigurationSource(resources[i]));
                }
            }
            final JsonviewConfigurationSaxReader reader = new JsonviewConfigurationSaxReader(sources);
            return reader.readConfiguration();
        } catch (IOException e) {
            throw new JsonviewException("Happen IOException when Spring ResourcePatternResolver get resource: %s", e.getMessage());
        }
    }

    /**
     * 根据默认的ObjectMapper创建JsonviewFactory
     *
     * @return JsonviewFactory
     */
    public JsonviewFactory createJsonviewFactory() {
        return new JsonviewFactory(new ObjectMapper(), createJsonviewConfiguration());
    }

    /**
     * 根据自定义的ObjectMapper创建JsonviewFactory
     *
     * @param objectMapper
     * @return JsonviewFactory
     */
    public JsonviewFactory createJsonviewFactory(ObjectMapper objectMapper) {
        return new JsonviewFactory(objectMapper, createJsonviewConfiguration());
    }
}
