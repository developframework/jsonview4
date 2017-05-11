package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;
import lombok.Getter;

/**
 * 数组元素处理器
 *
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class ObjectInArrayProcessor extends ObjectProcessor {

    @Getter
    protected int size;

    public ObjectInArrayProcessor(ProcessContext context, ObjectElement element, Expression parentExpression, int size) {
        super(context, element, parentExpression);
        this.size = size;
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }
}
