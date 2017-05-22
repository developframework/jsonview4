package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.exception.ConfigurationSourceException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件配置源
 * @author qiuzhenhao
 */
public class FileConfigurationSource implements ConfigurationSource{

    private String filename;

    public FileConfigurationSource(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.getClass().getResourceAsStream(filename);
        if (is == null) {
            throw new ConfigurationSourceException(filename);
        }
        return is;
    }

    @Override
    public String getSourceName() {
        return filename;
    }
}
