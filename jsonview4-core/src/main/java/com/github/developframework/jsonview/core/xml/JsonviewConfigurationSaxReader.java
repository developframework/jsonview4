package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.exception.JsonviewParseXmlException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * 配置解析器
 * @author qiuzhenhao
 */
@Slf4j
public class JsonviewConfigurationSaxReader {

    @Getter
    private Set<ConfigurationSource> sources;

    public JsonviewConfigurationSaxReader(String config) {
        this.sources = new HashSet<>(1);
        sources.add(new FileConfigurationSource(config));
    }

    public JsonviewConfigurationSaxReader(ConfigurationSource source) {
        this.sources = new HashSet<>(1);
        sources.add(source);
    }

    public JsonviewConfigurationSaxReader(Set<ConfigurationSource> sources) {
        this.sources = sources;
    }

    /**
     * 读配置
     * @return 生成的配置项
     */
    public JsonviewConfiguration readConfiguration() {
        JsonviewConfiguration jsonviewConfiguration = new JsonviewConfiguration();
        ConfigurationSaxParseHandler handler = new ConfigurationSaxParseHandler(jsonviewConfiguration);
        for (ConfigurationSource source : sources) {
            handleSingleSource(handler, source);
            log.info("Jsonview framework loaded the configuration source \"{}\".", source.getSourceName());
        }
        return jsonviewConfiguration;
    }

    /**
     * 处理单个源
     * @param handler handler
     * @param source 源
     */
    private void handleSingleSource(ConfigurationSaxParseHandler handler, ConfigurationSource source) {
        try {
            InputStream is = source.getInputStream();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(is, handler);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonviewParseXmlException("Jsonview Framework parse configuration source \"%s\" happened error: %s", source.getSourceName(), e.getMessage());
        }
    }
}
