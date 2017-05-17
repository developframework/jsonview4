package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.IfProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * if节点
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class IfElement extends ContainerFunctionalElement {

    @Getter
    private String conditionValue;
    @Setter
    private ElseElement elseElement;

    public IfElement(JsonviewConfiguration configuration, String namespace, String templateId, String conditionValue) {
        super(configuration, namespace, templateId);
        this.conditionValue = conditionValue;
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new IfProcessor(processContext, this, parentExpression, parentNode);
    }

    public Optional<ElseElement> getElseElement() {
        return Optional.ofNullable(elseElement);
    }

}
