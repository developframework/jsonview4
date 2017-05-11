package com.github.developframework.jsonview.core.data;

import com.github.developframework.expression.Expression;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据定义
 *
 * @author qiuzhenhao
 * @date 2017/5/6
 */
@Getter
public class DataDefinition {

    public static final DataDefinition EMPTY_DATA_DEFINITION = new DataDefinition(null);

    /* 功能符号 */
    private FunctionSign functionSign;
    /* 表达式 */
    private Expression expression;

    public DataDefinition(String dataValue) {
        if (StringUtils.isBlank(dataValue)) {
            expression = Expression.EMPTY_EXPRESSION;
        } else {
            dataValue = dataValue.trim();
            char firstChar = dataValue.charAt(0);
            boolean hasFunctionSign = false;
            for (FunctionSign sign : FunctionSign.values()) {
                if (sign.getSign() == firstChar) {
                    this.functionSign = sign;
                    hasFunctionSign = true;
                    break;
                }
            }
            if (hasFunctionSign) {
                this.expression = Expression.parse(dataValue.substring(1));
            } else {
                this.expression = Expression.parse(dataValue);
            }
        }
    }
}