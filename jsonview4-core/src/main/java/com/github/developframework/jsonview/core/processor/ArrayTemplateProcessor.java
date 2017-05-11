package com.github.developframework.jsonview.core.processor;

import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.ArrayElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;

/**
 * 数组模板处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class ArrayTemplateProcessor extends ArrayProcessor{


    public ArrayTemplateProcessor(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, ArrayElement arrayElement) {
        super(processContext, arrayElement, jsonviewTemplate.getDataDefinition().getExpression());
        element.copyChildElement(jsonviewTemplate);
        element.getItemObjectElement().copyChildElement(jsonviewTemplate);
    }

    @Override
    protected Expression childExpression(Expression parentExpression) {
        return parentExpression;
    }
}
