package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.OneToOneObjectProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;

import java.util.Optional;

/**
 * 一对一对象链接节点
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class OneToOneObjectElement extends ObjectElement{

    public OneToOneObjectElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Optional<Processor<? extends Element, ? extends JsonNode>> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        OneToOneObjectProcessor processor = new OneToOneObjectProcessor(processContext, this, parentExpression);
        Optional<Object> optional = processContext.getDataModel().getData(processor.getExpression());
        if (optional.isPresent()) {
            final ObjectNode objectNode = parentNode.putObject(this.showName());
            processor.setNode(objectNode);
            return Optional.of(processor);
        }
        if (!nullHidden) {
            parentNode.putNull(this.showName());
        }
        return Optional.empty();
    }
}
