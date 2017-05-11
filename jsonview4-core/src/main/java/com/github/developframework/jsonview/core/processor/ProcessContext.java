package com.github.developframework.jsonview.core.processor;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 处理过程上下文
 * @author qiuzhenhao
 * @date 2017/5/6
 */
@Getter
@Setter
public class ProcessContext {

    /* 数据模型 */
    private DataModel dataModel;

    /* 配置 */
    private JsonviewConfiguration jsonviewConfiguration;

    /* 扩展口Map */
    private Map<String, ExtendPortProcessor.ExtendCallback> extendPortMap = new HashMap<>();

    public void pushExtendCallback(String portName, ExtendPortProcessor.ExtendCallback callback) {
        extendPortMap.put(portName, callback);
    }

    protected Optional<ExtendPortProcessor.ExtendCallback> getExtendCallback(String port) {
        return Optional.ofNullable(extendPortMap.get(port));
    }
}
