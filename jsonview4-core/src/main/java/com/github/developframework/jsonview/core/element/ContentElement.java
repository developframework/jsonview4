package com.github.developframework.jsonview.core.element;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 内容节点基类
 *
 * @author qiuzhenhao
 * @date 2017/5/6
 */
@Getter
public abstract class ContentElement extends Element {

    @Setter
    protected DataDefinition dataDefinition;

    protected String alias;

    protected boolean nullHidden;

    public ContentElement(JsonviewConfiguration configuration, String namespace, String templateId, DataDefinition dataDefinition, String alias) {
        super(configuration, namespace, templateId);
        this.dataDefinition = dataDefinition;
        this.alias = alias;
    }

    public void setNullHidden(String nullHiddenStr) {
        this.nullHidden = StringUtils.isBlank(nullHiddenStr) ? false : new Boolean(nullHiddenStr).booleanValue();
    }

    /**
     * 生成显示名称
     * @return 显示名称
     */
    public String showName() {
        if (StringUtils.isNotBlank(alias)) {
            return alias;
        }
        final String expressionString = expressionString();
        PropertyNamingStrategy strategy = configuration.getObjectMapper().getPropertyNamingStrategy();
        if(strategy == null) {
            return expressionString;
        }
        return strategy.nameForField(null, null, expressionString);
    }

    private String expressionString() {
        Expression expression = dataDefinition.getExpression();
        if (expression instanceof ObjectExpression) {
            return ((ObjectExpression) expression).getPropertyName();
        } else if (expression instanceof ArrayExpression) {
            ArrayExpression arrayExpression = ((ArrayExpression) expression);
            return arrayExpression.getPropertyName() + "_" + arrayExpression.getIndex();
        } else {
            return "aaa";
        }
    }

}
