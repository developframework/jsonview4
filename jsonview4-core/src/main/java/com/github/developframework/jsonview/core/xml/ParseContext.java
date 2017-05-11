package com.github.developframework.jsonview.core.xml;

import com.github.developframework.jsonview.core.JsonviewConfiguration;
import com.github.developframework.jsonview.core.element.Element;
import com.github.developframework.jsonview.core.element.IfElement;
import com.github.developframework.jsonview.core.element.JsonviewTemplate;
import com.github.developframework.jsonview.core.element.JsonviewTemplatePackage;
import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

/**
 * 解析过程上下文
 * @author qiuzhenhao
 * @date 2017/5/7
 */
@Getter
class ParseContext {

    /* 配置 */
    private JsonviewConfiguration configuration;
    /* 当前模板包 */
    @Setter
    private JsonviewTemplatePackage currentTemplatePackage;
    @Setter
    private JsonviewTemplate currentTemplate;
    @Setter
    private IfElement currentIfElement;
    /* 节点栈 */
    private Stack<Element> stack;

    public ParseContext(JsonviewConfiguration configuration) {
        this.configuration = configuration;
        this.stack = new Stack<>();
    }
}
