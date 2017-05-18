package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.PropertyElement;

import java.util.Objects;

/**
 * unix时间戳型属性节点处理器
 * @author qiuzhenhao
 */
public class UnixTimestampPropertyProcessor extends DatePropertyProcessor {

    public UnixTimestampPropertyProcessor(ProcessContext processContext, PropertyElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected void handle(ObjectNode parentNode, Class<?> clazz, Object value, String showName) {
        java.util.Date date = transformDate(clazz, value);
        if (Objects.isNull(date)) {
            parentNode.putNull(showName);
            return;
        }
        parentNode.put(showName, date.getTime() / 1000);
    }
}
