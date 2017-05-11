package com.github.developframework.jsonview.core.xml;

import java.io.IOException;
import java.io.InputStream;

/**
 * 配置源接口
 * @author qiuzhenhao
 * @date 2017/5/7
 */
public interface ConfigurationSource {

    /**
     * 获得源的输入流
     * @return 输入流
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;

    /**
     * 获得源名称
     * @return 源名称
     */
    String getSourceName();
}
