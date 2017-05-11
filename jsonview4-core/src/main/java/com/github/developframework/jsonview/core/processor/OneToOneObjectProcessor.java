package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.ObjectElement;
import com.github.developframework.jsonview.core.exception.OneToOneSizeNotEqualException;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 数组一对一拼接节点处理器
 * @author qiuzhenhao
 * @date 2017/5/9
 */
public class OneToOneObjectProcessor extends ObjectProcessor implements OneToOneProcessor {

    @Getter
    private int index;

    public OneToOneObjectProcessor(ProcessContext processContext, ObjectElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        ObjectInArrayProcessor objectInArrayProcessor = (ObjectInArrayProcessor) parentProcessor;
        this.checkSize(objectInArrayProcessor.getSize());
        this.setIndex(((ArrayExpression) parentProcessor.getExpression()).getIndex());
        super.process(parentProcessor);
    }

    @Override
    public void checkSize(int parentArraySize) {
        Optional<Object> objOptional = processContext.getDataModel().getData(expression);
        if (objOptional.isPresent()) {
            int size = 0;
            Object obj = objOptional.get();
            if (obj.getClass().isArray()) {
                size = ((Object[]) obj).length;
            } else if (obj instanceof Collection<?>) {
                size = ((List<?>) obj).size();
            }
            if (size != parentArraySize) {
                throw new OneToOneSizeNotEqualException("object-one-to-one", element.getNamespace(), element.getTemplateId());
            }
        }
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
        this.expression = ArrayExpression.fromObject((ObjectExpression) expression, index);
    }
}
