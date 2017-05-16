package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.ArrayElement;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;

/**
 * 数组模板处理器
 * @author qiuzhenhao
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

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        // 始终为true
        return true;
    }
}
