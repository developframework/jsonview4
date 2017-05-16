package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.PropertyProcessor;
import com.github.developframework.jsonview.core.processor.UnixTimestampPropertyProcessor;

import java.util.Optional;

/**
 * unix时间戳型属性节点
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class UnixTimestampPropertyElement extends PropertyElement{

    public UnixTimestampPropertyElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        PropertyProcessor processor = new UnixTimestampPropertyProcessor(processContext, this, parentExpression);
        processor.setNode(parentNode);
        return processor;
    }
}
