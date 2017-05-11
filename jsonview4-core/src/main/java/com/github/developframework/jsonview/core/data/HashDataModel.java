package com.github.developframework.jsonview.core.data;

import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ExpressionUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Hash数据模型
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class HashDataModel implements DataModel{

    @Getter
    private Map<String, Object> dataMap = new HashMap<String, Object>();

    public HashDataModel() {
    }

    public HashDataModel(Map<String, Object> dataMap) {
        this.dataMap.putAll(dataMap);
    }

    @Override
    public void putData(String dataName, Object data) {
        this.dataMap.put(dataName, data);
    }

    @Override
    public Optional<Object> getData(Expression expression) {
        return Optional.ofNullable(ExpressionUtils.getValue(dataMap, expression));
    }

    @Override
    public Optional<Object> getData(String expressionValue) {
        return getData(Expression.parse(expressionValue));
    }


    /**
     * 构造只有一个数据的DataModel
     * @param dataName 数据名称
     * @param data 数据值
     * @return
     */
    public static final DataModel singleton(String dataName, Object data) {
        DataModel dataModel = new HashDataModel();
        dataModel.putData(dataName, data);
        return dataModel;
    }

}
