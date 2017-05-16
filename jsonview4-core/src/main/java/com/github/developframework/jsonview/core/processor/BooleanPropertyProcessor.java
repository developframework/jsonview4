package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.Expression;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.PropertyElement;

import java.util.HashSet;
import java.util.Set;

/**
 * 布尔型属性节点处理器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
public class BooleanPropertyProcessor extends PropertyProcessor{

    /* 允许类型列表 */
    private static final Set<Class<?>> ACCEPT_CLASS_SET = new HashSet<>(8);

    static {
        ACCEPT_CLASS_SET.add(boolean.class);
        ACCEPT_CLASS_SET.add(Boolean.class);
        ACCEPT_CLASS_SET.add(int.class);
        ACCEPT_CLASS_SET.add(Integer.class);
        ACCEPT_CLASS_SET.add(long.class);
        ACCEPT_CLASS_SET.add(Long.class);
        ACCEPT_CLASS_SET.add(short.class);
        ACCEPT_CLASS_SET.add(Short.class);
    }

    public BooleanPropertyProcessor(ProcessContext processContext, PropertyElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        return true;
    }

    @Override
    protected boolean support(Class<?> sourceClass) {
        return ACCEPT_CLASS_SET.contains(sourceClass);
    }

    @Override
    protected void handle(ObjectNode parentNode, Class<?> clazz, Object value, String showName) {
        boolean v = false;
        if (clazz == Boolean.class) {
            v = ((Boolean) value).booleanValue();
        } else if (clazz == Integer.class) {
            v = ((Integer) value).intValue() != 0;
        } else if (clazz == Long.class) {
            v = ((Long) value).longValue() != 0;
        } else if (clazz == Short.class) {
            v = ((Short) value).shortValue() != 0;
        } else {
            parentNode.putNull(showName);
            return;
        }
        parentNode.put(showName, v);
    }
}
