package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.processor.IncludeProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * 包含功能节点
 * @author qiuzhenhao
 * @date 2017/5/8
 */
@Getter
@Setter
public class IncludeElement extends FunctionalElement {

    /* 命名空间 */
    private String targetNamespace;
    /* 模板ID */
    private String targetTemplateId;

    public IncludeElement(JsonviewConfiguration configuration, String namespace, String templateId, String targetNamespace, String targetTemplateId) {
        super(configuration, namespace, templateId);
        this.targetNamespace = targetNamespace;
        this.targetTemplateId = targetTemplateId;
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new IncludeProcessor(processContext, this, parentNode, parentExpression);
    }
}
