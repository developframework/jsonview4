package com.github.developframework.jsonview.spring;

import com.github.developframework.jsonview.core.xml.ConfigurationSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * spring resource配置源
 *
 * @author qiuzhenhao
 */
public class SpringResourceConfigurationSource implements ConfigurationSource {

    /* spring的Resource接口 */
    private Resource resource;

    public SpringResourceConfigurationSource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public String getSourceName() {
        return resource.getFilename();
    }
}
