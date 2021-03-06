package com.github.developframework.jsonview.core.data;

import com.github.developframework.expression.Expression;

import java.io.Serializable;
import java.util.Optional;

/**
 * 数据模型接口
 * @author qiuzhenhao
 */
public interface DataModel extends Serializable {

    /**
     * 放入数据
     * @param dataName 数据名称
     * @param data 数据值
     */
    void putData(String dataName, Object data);

    /**
     * 获得数据
     * @param expression 表达式定义
     * @return 数据值
     */
    Optional<Object> getData(Expression expression);

    /**
     * 获得数据
     * @param expressionValue 表达式字符串
     * @return 数据值
     */
    Optional<Object> getData(String expressionValue);

    /**
     * 获得数据 如果没有抛出异常
     * @param expression 表达式定义
     * @return 数据值
     */
    Object getDataRequired(Expression expression);

    /**
     * 获得数据 如果没有抛出异常
     * @param expressionValue 表达式字符串
     * @return 数据值
     */
    Object getDataRequired(String expressionValue);

}
