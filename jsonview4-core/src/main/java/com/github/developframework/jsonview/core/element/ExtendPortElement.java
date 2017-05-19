package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.ExtendPortProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import lombok.Getter;

/**
 * 扩展端口节点
 * @author qiuzhenhao
 */
public class ExtendPortElement extends FunctionalElement{

    @Getter
    private String portName;

    public ExtendPortElement(JsonviewConfiguration configuration, String namespace, String templateId, String portName) {
        super(configuration, namespace, templateId);
        this.portName = portName;
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new ExtendPortProcessor(processContext, this, parentNode, parentExpression);
    }
}
