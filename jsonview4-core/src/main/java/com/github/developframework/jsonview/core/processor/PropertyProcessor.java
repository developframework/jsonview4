package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.dynamic.PropertyConverter;
import com.github.developframework.jsonview.core.data.DataModel;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.PropertyElement;
import com.github.developframework.jsonview.core.exception.JsonviewException;

import java.util.Optional;

/**
 * 抽象的属性节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public abstract class PropertyProcessor extends ContentProcessor<PropertyElement, JsonNode>{

    public PropertyProcessor(ProcessContext processContext, PropertyElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        DataModel dataModel = processContext.getDataModel();
        Optional<Object> valueOptional = dataModel.getData(expression);
        ObjectNode parentNode = (ObjectNode) parentProcessor.getNode();
        final String showName = super.element.showName();
        if (!valueOptional.isPresent()) {
            // 处理null-hidden
            if (!element.isNullHidden()) {
                parentNode.putNull(showName);
            }
            return;
        }
        valueOptional.ifPresent(value -> {
            // 处理converter
            Optional<Object> optional = element.getConverterValue().map(converterValue -> {
                Optional<Object> converterOptional = dataModel.getData(converterValue);
                Object obj = converterOptional.orElseGet(() -> {
                    try {
                        return Class.forName(converterValue).newInstance();
                    } catch (ClassNotFoundException e) {
                        throw new JsonviewException("The converter's Class \"%s\" not found, and it's also not a expression.", converterValue);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new JsonviewException("Can't new converter instance.");
                    }
                });
                if(obj instanceof PropertyConverter) {
                    return ((PropertyConverter) obj).convert(value);
                } else {
                    throw new JsonviewException(String.format("\"%s\" is not a PropertyConverter instance.", obj.toString()));
                }
            });
            final Object newValue = optional.orElse(value);
            Class<?> valueClass = newValue.getClass();
            if (support(valueClass)) {
                handle(parentNode, valueClass, newValue, showName);
            }
        });
    }

    /**
     * 判断是否支持sourceClass类型
     * @param sourceClass
     * @return
     */
    protected abstract boolean support(Class<?> sourceClass);

    /**
     * 属性具体处理
     *
     * @param parentNode 父树节点
     * @param sourceClass sourceClass
     * @param value 值
     * @param showName 显示的名称
     */
    protected abstract void handle(ObjectNode parentNode, Class<?> sourceClass, Object value, String showName);
}
