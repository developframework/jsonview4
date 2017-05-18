package com.github.developframework.jsonview.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.data.DataModel;
import com.github.developframework.jsonview.core.element.ArrayElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import com.github.developframework.jsonview.core.exception.JsonviewException;
import com.github.developframework.jsonview.core.processor.ArrayProcessor;
import com.github.developframework.jsonview.core.processor.ArrayTemplateProcessor;
import com.github.developframework.jsonview.core.processor.ProcessContext;
import com.github.developframework.jsonview.core.processor.TemplateProcessor;

import java.util.List;
import java.util.Optional;

/**
 * 默认的Json生成器
 * @author qiuzhenhao
 */
class DefaultJsonProducer implements JsonProducer{

    private JsonviewConfiguration jsonviewConfiguration;

    DefaultJsonProducer(JsonviewConfiguration jsonviewConfiguration) {
        this.jsonviewConfiguration = jsonviewConfiguration;
    }

    @Override
    public String createJson(DataModel dataModel, String namespace, String templateId) {
        return createJson(dataModel, namespace, templateId, false);
    }

    @Override
    public String createJson(DataModel dataModel, String namespace, String templateId, boolean isPretty) {
        JsonNode root = constructRootTree(dataModel, namespace, templateId);
        try {
            if (isPretty) {
                return jsonviewConfiguration.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(root);
            } else {
                return jsonviewConfiguration.getObjectMapper().writeValueAsString(root);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void printJson(JsonGenerator jsonGenerator, DataModel dataModel, String namespace, String templateId) {
        JsonNode root = constructRootTree(dataModel, namespace, templateId);
        try {
            jsonviewConfiguration.getObjectMapper().writeValue(jsonGenerator, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造json树对象
     * @param dataModel
     * @param namespace
     * @param id
     * @return
     */
    private JsonNode constructRootTree(DataModel dataModel, String namespace, String id) {
        JsonviewTemplate jsonviewTemplate = jsonviewConfiguration.extractTemplate(namespace, id);
        ProcessContext processContext = new ProcessContext();
        processContext.setDataModel(dataModel);
        processContext.setJsonviewConfiguration(jsonviewConfiguration);

        DataDefinition templateDataDefinition = jsonviewTemplate.getDataDefinition();

        if(templateDataDefinition != null) {
            Optional<Object> rootObjectOptional = dataModel.getData(templateDataDefinition.getExpression());
            if (rootObjectOptional.isPresent()) {
                Object rootObject = rootObjectOptional.get();
                if (rootObject.getClass().isArray() || rootObject instanceof List) {
                    // 视为数组模板
                    return constructRootArrayNodeTree(processContext, jsonviewTemplate, rootObject);
                } else {
                    // 视为对象模板
                    return constructRootObjectNodeTree(processContext, jsonviewTemplate, rootObject);
                }
            } else {
                throw new JsonviewException("Root data must not null.");
            }
        } else {
            // 视为对象模板
            return constructRootObjectNodeTree(processContext, jsonviewTemplate, null);
        }
    }

    private ObjectNode constructRootObjectNodeTree(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, Object value) {
        ObjectNode rootNode = jsonviewConfiguration.getObjectMapper().createObjectNode();
        TemplateProcessor templateProcessor = new TemplateProcessor(processContext, jsonviewTemplate, jsonviewTemplate.getDataDefinition().getExpression());
        templateProcessor.setValue(value);
        templateProcessor.setNode(rootNode);
        templateProcessor.process(null);
        return rootNode;
    }

    private ArrayNode constructRootArrayNodeTree(ProcessContext processContext, JsonviewTemplate jsonviewTemplate, Object value) {
        ArrayNode rootNode = jsonviewConfiguration.getObjectMapper().createArrayNode();
        ArrayElement arrayElement = new ArrayElement(jsonviewConfiguration, jsonviewTemplate.getNamespace(), jsonviewTemplate.getTemplateId(), jsonviewTemplate.getDataDefinition(), null);
        arrayElement.setMapFunctionValue(jsonviewTemplate.getMapFunctionValue());
        ArrayProcessor arrayProcessor = new ArrayTemplateProcessor(processContext, jsonviewTemplate, arrayElement);
        arrayProcessor.setValue(value);
        arrayProcessor.setNode(rootNode);
        arrayProcessor.process(null);
        return rootNode;
    }
}
