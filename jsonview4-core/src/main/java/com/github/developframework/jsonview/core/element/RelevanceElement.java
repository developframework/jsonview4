package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.Processor;
import com.github.developframework.jsonview.core.processor.RelevanceProcessor;
import lombok.Getter;
import lombok.Setter;

/**
 * 关联节点
 *
 * @author qiuzhenhao
 */
public class RelevanceElement extends ArrayElement {

    @Getter
    @Setter
    private String relFunctionValue;

    public RelevanceElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new RelevanceProcessor(processContext, this, parentExpression);
    }
}
