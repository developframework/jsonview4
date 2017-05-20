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
import org.apache.commons.lang3.StringUtils;

/**
 * 关联节点
 *
 * @author qiuzhenhao
 */
@Getter
public class RelevanceElement extends ArrayElement {

    @Setter
    private String relFunctionValue;

    private RelevanceType relevanceType;

    public RelevanceElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId, dataDefinition, alias);
    }

    @Override
    public Processor<? extends Element, ? extends JsonNode> createProcessor(ProcessContext processContext, ObjectNode parentNode, Expression parentExpression) {
        return new RelevanceProcessor(processContext, this, parentExpression);
    }

    /**
     * 设置关联类型
     * @param relevanceTypeValue
     */
    public void setRelevanceType(String relevanceTypeValue) {
        if (StringUtils.isNotBlank(relevanceTypeValue)) {
            this.relevanceType = RelevanceType.valueOf(relevanceTypeValue.toUpperCase());
        } else {
            this.relevanceType = RelevanceType.AUTO;
        }
    }

    /**
     * 关联类型
     */
    public enum RelevanceType {

        AUTO,       // 依据数据数量自动选择采用对象或数组结构
        SINGLE,     // 明确单项数据选择采用对象结构
        MULTIPLE    // 明确多项数据选择采用数组结构
    }
}
