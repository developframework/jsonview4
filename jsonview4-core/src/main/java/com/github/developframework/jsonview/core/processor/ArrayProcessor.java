package com.github.developframework.jsonview.core.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.expression.ArrayExpression;
import com.github.developframework.expression.Expression;
import com.github.developframework.expression.ObjectExpression;
import com.github.developframework.jsonview.core.data.DataModel;
import com.github.developframework.jsonview.core.dynamic.MapFunction;
import com.github.developframework.jsonview.core.element.ArrayElement;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.exception.JsonviewException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 数组节点处理器
 *
 * @author qiuzhenhao
 */
@Slf4j
public class ArrayProcessor extends ContainerProcessor<ArrayElement, ArrayNode> {

    private Optional<MapFunction> mapFunctionOptional;

    public ArrayProcessor(ProcessContext processContext, ArrayElement element, Expression parentExpression) {
        super(processContext, element, parentExpression);
        this.mapFunctionOptional = mapFunction(element.getMapFunctionValueOptional(), processContext.getDataModel());
        if (mapFunctionOptional.isPresent()) {
            if (!element.isChildElementEmpty()) {
                log.warn("The child element invalid, because you use \"map-function\" attribute.");
            }
        }
    }

    @Override
    protected boolean prepare(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        Optional<Object> optional = processContext.getDataModel().getData(expression);
        if (optional.isPresent()) {
            this.node = ((ObjectNode) parentProcessor.getNode()).putArray(element.showName());
            return true;
        }
        if (!element.isNullHidden()) {
            ((ObjectNode) parentProcessor.getNode()).putNull(element.showName());
        }
        return false;
    }

    @Override
    protected void handleCoreLogic(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor) {
        final Optional<Object> valueOptional = processContext.getDataModel().getData(expression);
        if (valueOptional.isPresent()) {
            this.value = valueOptional.get();
            int size = 0;
            if (value.getClass().isArray()) {
                size = ((Object[]) value).length;
            } else if (value instanceof Collection<?>) {
                size = ((Collection<?>) value).size();
            }
            for (int i = 0; i < size; i++) {
                single(ArrayExpression.fromObject((ObjectExpression) expression, i), size);
            }
        }
    }

    public void process(ContentProcessor<? extends Element, ? extends JsonNode> parentProcessor, List<ArrayExpression> expressions) {
        if(prepare(parentProcessor)) {
            for (ArrayExpression arrayExpression : expressions) {
                single(arrayExpression, expressions.size());
            }
        }
    }

    private void single(ArrayExpression arrayExpression, int size) {
        if (element.isChildElementEmpty() || mapFunctionOptional.isPresent()) {
            empty(arrayExpression.getIndex());
        } else {
            final ObjectInArrayProcessor childProcessor = new ObjectInArrayProcessor(processContext, element.getItemObjectElement(), arrayExpression, size);
            final ObjectNode objectNode = processContext.getJsonviewConfiguration().getObjectMapper().createObjectNode();
            childProcessor.setNode(objectNode);
            childProcessor.process(null);
            node.add(objectNode);
        }
    }

    private void empty(int index) {
        final Optional<Object> objectOptional = processContext.getDataModel().getData(ArrayExpression.fromObject((ObjectExpression) expression, index));
        if (!objectOptional.isPresent()) {
            node.addNull();
            return;
        }
        Object object = objectOptional.get();

        if (mapFunctionOptional.isPresent()) {
            object = mapFunctionOptional.get().apply(object, index);
        }

        if (object instanceof String) {
            node.add((String) object);
        } else if (object instanceof Integer) {
            node.add((Integer) object);
        } else if (object instanceof Long) {
            node.add((Long) object);
        } else if (object instanceof Short) {
            node.add((Short) object);
        } else if (object instanceof Boolean) {
            node.add((Boolean) object);
        } else if (object instanceof Float) {
            node.add((Float) object);
        } else if (object instanceof Double) {
            node.add((Double) object);
        } else if (object instanceof BigDecimal) {
            node.add((BigDecimal) object);
        } else if (object instanceof Character) {
            node.add((Character) object);
        } else if (object instanceof Byte) {
            node.add((Byte) object);
        } else {
            node.add(object.toString());
        }
    }

    protected Optional<MapFunction> mapFunction(Optional<String> mapFunctionValueOptional, DataModel dataModel) {
        if (mapFunctionValueOptional.isPresent()) {
            final String mapFunctionValue = mapFunctionValueOptional.get();
            Optional<Object> mapFunctionOptional = dataModel.getData(mapFunctionValue);
            Object obj = mapFunctionOptional.orElseGet(() -> {
                try {
                    return Class.forName(mapFunctionValue).newInstance();
                } catch (ClassNotFoundException e) {
                    throw new JsonviewException("The mapFunction's Class \"%s\" not found, and it's also not a expression.", mapFunctionValue);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new JsonviewException("Can't new mapFunction instance.");
                }
            });
            if (obj instanceof MapFunction) {
                return Optional.of(((MapFunction) obj));
            } else {
                throw new JsonviewException("\"%s\" is not a MapFunction instance.", obj.toString());
            }
        }
        return Optional.empty();
    }
}
