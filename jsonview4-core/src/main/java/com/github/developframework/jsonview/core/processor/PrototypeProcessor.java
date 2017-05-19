package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.dynamic.PropertyConverter;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.PrototypeElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.JsonviewException;

import java.io.IOException;
import java.util.Optional;

/**
 * 原型节点处理器
 *
 * @author qiuzhenhao
 */
public class PrototypeProcessor extends ContentProcessor<PrototypeElement, ObjectNode>{

    public PrototypeProcessor(ProcessContext processContext, PrototypeElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
        if(valueOptional.isPresent()) {
            this.value = valueOptional.get();
            return true;
        }
        if (!element.isNullHidden()) {
            node.putNull(element.showName());
        }
        return false;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        // 经过converter转化后的值
        Optional<Object> convertValueOptional = element.getConverterValue().map(converterValue -> {
            Optional<Object> converterOptional = processContext.getDataModel().getData(converterValue);
            Object obj = converterOptional.orElseGet(() -> {
                try {
                    return Class.forName(converterValue).newInstance();
                } catch (ClassNotFoundException e) {
                    throw new InvalidArgumentsException("converter", converterValue, "Class not found, and it's also not a expression.");
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new JsonviewException("Can't new converter instance.");
                }
            });
            if (obj instanceof PropertyConverter) {
                return ((PropertyConverter) obj).convert(value);
            } else {
                throw new InvalidArgumentsException("converter", converterValue, "It's not a PropertyConverter instance.");
            }
        });
        final Object convertValue = convertValueOptional.orElse(value);

        ObjectMapper objectMapper = processContext.getJsonviewConfiguration().getObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(convertValue);
            JsonNode jsonNode = objectMapper.readTree(json);
            node.set(element.showName(), jsonNode);
        } catch (IOException e) {
            throw new JsonviewException("Happen IOException on prototype handle.");
        }
    }
}
