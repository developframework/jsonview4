package com.github.developframework.jsonview.core.processor;

import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.ObjectElement;

/**
 * 虚拟对象节点处理器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class VirtualObjectProcessor extends ObjectProcessor{

    public VirtualObjectProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }
}
