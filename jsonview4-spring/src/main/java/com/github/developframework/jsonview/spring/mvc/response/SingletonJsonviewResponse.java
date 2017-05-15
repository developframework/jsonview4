package com.github.developframework.jsonview.spring.mvc.response;

/**
 * 单例数据JsonviewResponse
 * @author qiuzhenhao
 * @date 2017/5/11
 * @param <T> 数据类型
 */
public final class SingletonJsonviewResponse<T> extends EmptyJsonviewResponse{

    public SingletonJsonviewResponse(String namespace, String jsonviewId, String dataName, T data) {
        super(namespace, jsonviewId);
        this.dataModel.putData(dataName, data);
    }
}
