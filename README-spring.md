# Jsonview4-spring

## 1. 引入

```xml
<dependency>
	<groupId>com.github.developframework</groupId>
	<artifactId>jsonview4-spring</artifactId>
	<version>4.0.0</version>
</dependency>
```

## 2. 配置

### 2.1. 加载JsonviewFactory

```xml
<bean id="jsonviewFactory" class="com.github.developframework.jsonview.spring.JsonviewFactoryFactoryBean">
	<property name="configs">
		<set>
			<value>/jsonview/jsonview-student.xml</value>
			<value>/jsonview/jsonview-account.xml</value>
			<value>/jsonview/jsonview-class.xml</value>
		</set>
	</property>
</bean>
```

在配置文件较多时，上述方法比较麻烦，可以采用以下扫描配置文件包的方法缩减代码量。

使用`<jsonview:scan>`扫描文件包加载Jsonview configuration文件。

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:jsonview="https://github.com/developframework/jsonview4/schema"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.1.xsd
		https://github.com/developframework/jsonview4/schema
		https://github.com/developframework/jsonview4/schema/jsonview-spring.xsd">	
	
	<jsonview:scan id="jsonviewFactory" locations="classpath:jsonview/*.xml" />
	
</beans>
```

- 此方法需要添加命名空间`xmlns:jsonview="https://github.com/developframework/jsonview4/schema"`
- `classpath:jsonview/*.xml`为通配加载jsonview文件夹下的所有配置文件。

### 2.2. 支持springmvc

当前版本下提供两个`HandlerMethodReturnValueHandler` 实现类，分别对应于Controller方法返回`DataModel` 和`JsonviewResponse`的处理。

```xml
<mvc:annotation-driven>
  <mvc:return-value-handlers>  
    <bean class="com.github.developframework.jsonview.spring.mvc.DataModelReturnValueHandler" />
    <bean class="com.github.developframework.jsonview.spring.mvc.JsonviewResponseReturnValueHandler" />
  </mvc:return-value-handlers>  
</mvc:annotation-driven>
```

#### 2.2.1. JsonviewResponseReturnValueHandler

Controller方法以`JsonviewResponse`对象返回将会被交由` JsonviewResponseReturnValueHandler` 处理。

```java
@Controller
public class HelloController {
    
    @GetMapping("/hello")
    public JsonviewResponse hello() {
        JsonviewResponse res = new EmptyJsonviewResponse("jsonview-demo", "hello-view");
        res.putData("sayHello", "Hello jsonview4-spring!");
        return res;
    }
}
```

`JsonviewResponse`提供以下实现：

| 实现                        | 说明                    |
| ------------------------- | --------------------- |
| EmptyJsonviewResponse     | 提供空的内置DataModel       |
| SingletonJsonviewResponse | 提供可优先包含一个数据的DataModel |

可以继续继承方便自己的使用。

#### 2.2.2. DataModelReturnValueHandler

Controller方法以`DataModel`对象返回将会被交由` DataModelReturnValueHandler` 处理。

```java
@Controller
public class HelloController {

    @JsonviewNamespace("jsonview-demo")
    @TemplateId("hello-view")
    @GetMapping("/hello")
    public DataModel hello() {
        return HashDataModel.singleton("sayHello", "Hello jsonview4-spring!");
    }
}
```

`@JsonviewNamespace` 用于申明使用哪个命名空间，该注解也可以加在类上，对类的所有方法起作用。

`@TemplateId` 用于申明使用哪个模板ID。

**在使用返回`DataModel` 模式时，这两个注解必填。**

