package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.dynamic.PropertyConverter;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.PropertyElement;
import com.github.developframework.jsonview.core.exception.InvalidArgumentsException;
import com.github.developframework.jsonview.core.exception.JsonviewException;

import java.util.Optional;

/**
 * 抽象的属性节点处理器
 *
 * @author qiuzhenhao
 */
public abstract class PropertyProcessor extends ContentProcessor<PropertyElement, ObjectNode> {

    public PropertyProcessor(ProcessContext processContext, PropertyElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
        if (valueOptional.isPresent()) {
            this.value = valueOptional.get();
            this.node = (ObjectNode) parentProcessor.getNode();
            return true;
        }
        if (!element.isNullHidden()) {
            ((ObjectNode) parentProcessor.getNode()).putNull(element.showName());
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
        Class<?> convertValueClass = convertValue.getClass();
        if (support(convertValueClass)) {
            handle(this.node, convertValueClass, convertValue, element.showName());
        }
    }

    /**
     * 判断是否支持sourceClass类型
     *
     * @param sourceClass 源类型
     * @return 是否支持
     */
    protected abstract boolean support(Class<?> sourceClass);

    /**
     * 属性具体处理
     *
     * @param parentNode  父树节点
     * @param sourceClass sourceClass
     * @param value       值
     * @param showName    显示的名称
     */
    protected abstract void handle(ObjectNode parentNode, Class<?> sourceClass, Object value, String showName);
}
