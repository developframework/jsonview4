package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.data.DataDefinition;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;

/**
 * 模板节点解析器
 * @author qiuzhenhao
 * @date 2017/5/8
 */
class TemplateElementSaxParser extends ContainerElementSaxParser<JsonviewTemplate>{

    TemplateElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "template";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String id = attributes.getValue("id").trim();
        final String data = attributes.getValue("data");
        final String extend = attributes.getValue("extend");
        final String mapFunctionValue = attributes.getValue("map-function");
        final JsonviewTemplate jsonviewTemplate = new JsonviewTemplate(jsonviewConfiguration, parseContext.getCurrentTemplatePackage().getNamespace(), id);
        if (StringUtils.isNotBlank(data)) {
            jsonviewTemplate.setDataDefinition(new DataDefinition(data));
        } else {
            jsonviewTemplate.setDataDefinition(DataDefinition.EMPTY_DATA_DEFINITION);
        }
        if (StringUtils.isNotBlank(extend)) {
            String defaultNamespace = parseContext.getCurrentTemplatePackage().getNamespace();
            jsonviewTemplate.setExtend(jsonviewTemplate.new Extend(extend.trim(), defaultNamespace));
        }
        if (StringUtils.isNotBlank(mapFunctionValue)) {
            jsonviewTemplate.setMapFunctionValue(mapFunctionValue);
        }
        handleForClass(jsonviewTemplate, attributes);
        parseContext.setCurrentTemplate(jsonviewTemplate);
        parseContext.getStack().push(jsonviewTemplate);
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        JsonviewTemplate jsonviewTemplate = (JsonviewTemplate) parseContext.getStack().pop();
        jsonviewTemplate.loadForClassAllProperty();
        parseContext.getCurrentTemplatePackage().push(jsonviewTemplate);
    }

    @Override
    protected JsonviewTemplate createElementInstance(ParseContext parseContext, DataDefinition dataDefinition, String alias) {
        // 无操作
        return null;
    }

    @Override
    protected void addOtherAttributes(JsonviewTemplate element, Attributes attributes) {
        // 无操作
    }
}
