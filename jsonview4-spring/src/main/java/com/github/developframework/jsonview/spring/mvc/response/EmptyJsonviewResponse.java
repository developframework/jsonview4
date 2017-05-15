package com.github.developframework.jsonview.spring.mvc.response;

import com.github.developframework.jsonview.core.data.HashDataModel;

/**
 * 空数据JsonviewResponse
 * @author qiuzhenhao
 * @date 2017/5/11
 */
public class EmptyJsonviewResponse extends JsonviewResponse{

    public EmptyJsonviewResponse(String namespace, String jsonviewId) {
        super(namespace, jsonviewId, new HashDataModel());
    }
}
