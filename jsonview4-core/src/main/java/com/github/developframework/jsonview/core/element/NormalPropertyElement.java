package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.NormalPropertyProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.PropertyProcessor;

import java.util.Optional;

/**
 * 普通的属性节点
 * @author qiuzhenhao
 * @date 2017/5/7
 */
public class NormalPropertyElement extends PropertyElement{

    public NormalPropertyElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        PropertyProcessor processor = new NormalPropertyProcessor(processContext, this, Processor.childExpression(this, parentExpression));
        processor.setNode(parentNode);
        return processor;
    }
}
