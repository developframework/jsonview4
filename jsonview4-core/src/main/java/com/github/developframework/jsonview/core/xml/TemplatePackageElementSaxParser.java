package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.JsonviewTemplatePackage;
import org.xml.sax.Attributes;

/**
 * 模板包解析器
 * @author qiuzhenhao
 */
class TemplatePackageElementSaxParser extends AbstractElementSaxParser{

    TemplatePackageElementSaxParser(JsonviewConfiguration jsonviewConfiguration) {
        super(jsonviewConfiguration);
    }

    @Override
    public String qName() {
        return "template-package";
    }

    @Override
    public void handleAtStartElement(ParseContext parseContext, Attributes attributes) {
        final String namespace = attributes.getValue("namespace").trim();
        parseContext.setCurrentTemplatePackage(new JsonviewTemplatePackage(namespace));
    }

    @Override
    public void handleAtEndElement(ParseContext parseContext) {
        parseContext.getConfiguration().putTemplatePackage(parseContext.getCurrentTemplatePackage());
    }
}
