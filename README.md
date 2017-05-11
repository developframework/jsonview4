# Jsonview4

## 1. 概述

Jsonview框架构建于jackson框架之上，实现通过XML文件配置来自定义json格式，大大提升了java生成json字符串的自由性，让开发模块化更加便捷快速。

### **1.1. 运行环境**

JDK8及以上

### **1.2. 使用方式**

maven

```xml
<dependency>
	<groupId>com.github.developframework</groupId>
	<artifactId>jsonview4-core</artifactId>
	<version>4.0.0</version>
</dependency>
```

### **1.3. 依赖项**

- commons-lang3.jar

- slf4j-api.jar

- jackson-core.jar

- jackson-databind.jar

- jackson-annotations.jar

- lombok.jar

## 2. HelloWorld

一个最简单的jsonview使用示例：

```java
public class Application {

    public static void main(String[] args) {
        JsonviewFactory factory = new JsonviewFactory("/jsonview/jsonview-demo.xml");
        JsonProducer jsonProducer = factory.getJsonProducer();
        DataModel dataModel = new HashDataModel();
        dataModel.putData("sayHello", "Hello Jsonview4!");
        String json = jsonProducer.createJson(dataModel, "jsonview-demo", "first-view");
        System.out.println(json);
    }
}
```

你需要一份jsonview XML配置，位置在上述声明的/jsonview/jsonview-demo.xml：

```xml
<jsonview-configuration xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xmlns="https://github.com/developframework/jsonview4/schema"
                        xsi:schemaLocation="
	https://github.com/developframework/jsonview4/schema
	https://github.com/developframework/jsonview4/schema/jsonview-configuration-4.0.xsd">

    <template-package namespace="jsonview-demo">

        <template id="first-view">
            <property data="sayHello"/>
        </template>
      
    </template-package>
</jsonview-configuration>
```

运行结果：

```json
{"sayHello":"Hello Jsonview4!"}
```

## **3. 概览**

### **3.1. java概览**

#### **3.1.1. DataModel**

`com.github.developframework.jsonview.data.DataModel`接口是jsonview框架的数据模型。用于装载需要在json视图中渲染的数据或函数接口实现，数据由键值对构成。接口提供存入和取出数据的方法。
目前实现类仅有`com.github.developframework.jsonview.data.HashDataModel`
存取数据范例：

```java
DataModel dataModel = new HashDataModel();
dataModel.putData("sayHello", "Hello Jsonview!");
Optional<Object> value = dataModel.getData("sayHello");
value.ifPresent(System.out::println);
```

#### **3.1.2. Expression**

`com.github.developframework.expression.Expression`类是jsonview框架从DataModel中提取数据的表达式。不论dataModel存的是java实体类还是Map对象都可以使用表达式取值。
范例：

- `student` 你可以从DataModel对象内取得名为student的对象
- `#student` 你可以从DataModel对象内 **强制从根路径** 取得名为student的对象
- `student.name` 你可以从DataModel对象内取得名为student的对象的name属性值
- `students[0]` 你可以从DataModel对象内取得名为students的数组内的第1个元素
- `student[0].name` 你可以从DataModel对象内取得名为students的数组内的第1个元素的name属性值

`Expression` 的详细使用请查看独立项目[expression](https://github.com/developframework/expression)

#### **3.1.3. JsonviewFactory**

`com.github.developframework.jsonview.core.JsonviewFactory`类是jsonview框架的构建工厂。使用jsonview框架的第一步就是建立该对象。
建立该对象需要提供配置文件路径的字符串，多份配置文件可以采用字符串数组。

```java
final String[] configs = {"config1.xml", "config2.xml"};
JsonviewFactory jsonviewFactory = new JsonviewFactory(configs);
```

#### **3.1.4. JsonviewConfiguration**

`com.github.developframework.jsonview.core.JsonviewConfiguration`类为Jsonview框架的总配置文件，可以从JsonviewFactory中得到该对象。

```java
JsonviewConfiguration jsonviewConfiguration = jsonviewFactory.getJsonviewConfiguration();
```

#### **3.1.5. JsonProducer**

`com.github.developframework.jsonview.core.JsonProducer`接口是json字符串建造类，执行一次生成json字符串的操作需要构建该对象。JsonProducer由JsonviewFactory生成。
该对象提供三个构建json字符串的方法：

```java
public String createJson(DataModel dataModel, String namespace, String id, boolean isPretty);
```

返回json字符串

- isPretty=true时可以美化json

```java
public String createJson(DataModel dataModel, String namespace, String id);
```

返回json字符串，不美化

```java
public void printJson(JsonGenerator generator, DataModel dataModel, String namespace, String id);
```

将json输出到ObjectMapper的generator对象

#### **3.1.6. Template**

`com.github.developframework.jsonview.core.element.Template`类，一个Template类的实例代表一种json的视图模板。它由`namespace`和`id`唯一确定。可以通过以下方法得到Template实例：

```java
Template template = jsonviewConfiguration.extractTemplate("namespace", "id");
```

#### **3.1.7. **JsonviewTemplatePackage

`com.github.developframework.jsonview.core.element.JsonviewTemplatePackage`类，一个JsonviewTemplatePackage实例是一个命名空间，可以装载若干个Template实例。推荐将Template按功能有序存放于JsonviewTemplatePackage。通过以下方法得到JsonviewTemplatePackage对象：

```java
JsonviewTemplatePackage jsonviewTemplatePackage = jsonviewConfiguration.getTemplatePackageByNamespace("namespace");
```

#### **3.1.8. 异常**

Jsonview框架的所有异常类。

| 异常                                | 说明                       |
| --------------------------------- | ------------------------ |
| JsonviewException                 | jsonview顶级异常             |
| ConfigurationSourceException      | 配置源异常                    |
| TemplateUndefinedException        | template未定义异常            |
| TemplatePackageUndefinedException | templatePackage未定义异常     |
| JsonviewParseXmlException         | 配置文件解析错误异常               |
| OneToOneSizeNotEqualException     | 使用one-to-one功能时数组大小不相等异常 |
| ResourceNotUniqueException        | 资源定义不唯一异常                |

### **3.2. XML概览**

#### **3.2.1. 结构**

Jsonview configuration 文档的结构如下：

```xml
<jsonview-configuration>
    <template-package namespace="">
    	<template id="">
    		<!-- 定义视图内容 -->
    	</template>
    	<template id="">
    		<!-- 定义视图内容 -->
    	</template>
    	<!-- 其它jsonview -->
    </template-package>
    <!-- 其它template-package -->
</jsonview-configuration>
```

在`<jsonview-configuration>`节点中你可以配置任意数量的`<template-package>`，代表不同的模板包，在`<template-package>`节点上你必须声明命名空间`namespace`属性，并且`namespace`是唯一的，不然会抛出`ResourceNotUniqueException`。

在每个`<template-package>`节点中你可以配置任意数量的`<template>`。每个`<template>`即代表了某一种json格式的视图，在`<template>`节点你必须声明id属性，并且id必须是唯一的，不然会抛出`ResourceNotUniqueException`。

Jsonview configuration文档不是唯一的，Jsonview框架允许你拥有多份的Jsonview configuration配置文档，文档的加载顺序不分先后。

#### **3.2.2. 标签**

基本型标签

- `<template>`
- `<object>`
- `<array>`
- `<property>`

功能型标签

- `<include>`
- `<object-one-to-one>`
- `<property-one-to-one>`
- `<object-virtual>`
- `<property-ignore>`
- `<extend-port>`
- `<if>`、 `<else>`

拓展型标签

- `<property-date>`

- `<property-unixtimestamp>`

- `<property-boolean>`

##### **3.2.2.1. 基本型标签**

###### a) template

  当你需要声明一个json格式模板时，你将会使用到`<template>`标签。

  ```xml
  <template id="" data="" for-class=""></jsonview>
  ```

| 属性        | 功能                                       | 是否必须 |
| --------- | ---------------------------------------- | ---- |
| id        | 声明模板编号，在命名空间中唯一                          | 是    |
| data      | 取值表达式                                    | 否    |
| for-class | 声明data表达式指向的对象类型                         | 否    |
| extend    | 声明继承的jsonview和端口，格式为namespace.id:port（namespace不填时默认为当前namespace） | 否    |

###### b) object

当你需要在json中构建一个对象结构时，你将会使用到`<object>`标签。

```xml
<object data="" alias="" for-class="" null-hidden="true"></object>
```

| 属性          | 功能                                | 是否必须 |
| ----------- | --------------------------------- | ---- |
| data        | 取值表达式                             | 是    |
| alias       | 别名，你可以重新定义显示名                     | 否    |
| for-class   | 声明data表达式指向的对象类型                  | 否    |
| null-hidden | true时表示表达式取的值为null时隐藏该节点，默认为false | 否    |

###### c) array

当你需要在json中构建一个数组结构时，你将会使用到`<array>`标签。

```xml
<array data="" alias="" for-class="" null-hidden="true"></array>
```

| 属性          | 功能                                | 是否必须 |
| ----------- | --------------------------------- | ---- |
| data        | 取值表达式                             | 是    |
| alias       | 别名，你可以重新定义显示名                     | 否    |
| for-class   | 声明data表达式指向的对象类型                  | 否    |
| null-hidden | true时表示表达式取的值为null时隐藏该节点，默认为false | 否    |

`<array>`标签可以没有子标签，这时表示数组为基本类型数组。

###### d) property

当你需要在json中构建一个普通属性结构时， 你将会使用到`<property>`标签。

```xml
<property data="" alias="" converter="" null-hidden="true"/>
```

| 属性          | 功能                                | 是否必须 |
| ----------- | --------------------------------- | ---- |
| data        | 取值表达式                             | 是    |
| alias       | 别名，你可以重新定义显示名                     | 否    |
| converter   | 类型转换器全限定类名或expression表达式          | 否    |
| null-hidden | true时表示表达式取的值为null时隐藏该节点，默认为false | 否    |

##### **3.2.2.2. 功能型标签**

###### a) include

Jsonview框架提供模块化设计json结构视图的功能。在一个`<template>`标签中你可以采用`<include>`标签来导入其它的`<template>`的结构内容，从而实现模块化单元分解。详见[5.3.节](#chapter53)

```xml
<include id="" namespace=""/>
```

| 属性        | 功能                        | 是否必须 |
| --------- | ------------------------- | ---- |
| id        | 需要导入的jsonview id          | 是    |
| namespace | jsonview的命名空间，不填默认为当前命名空间 | 否    |

###### b) object-one-to-one

该标签用于实现一对一链接对象功能。详见[5.4.1.节](#chapter541)。

```xml
<object-one-to-one data="" alias="" for-class="" null-hidden="true"></object-one-to-one>
```

| 属性          | 功能                                | 是否必须 |
| ----------- | --------------------------------- | ---- |
| data        | 取值表达式                             | 是    |
| alias       | 别名，你可以重新定义显示名                     | 否    |
| for-class   | 声明data表达式指向的对象类型                  | 否    |
| null-hidden | true时表示表达式取的值为null时隐藏该节点，默认为false | 否    |

###### b) property-one-to-one

该标签用于实现一对一链接属性功能。详见[5.4.1.节](#chapter541)。

```xml
<property-one-to-one data="" alias="" converter="" null-hidden="true" />
```

| 属性          | 功能                                | 是否必须 |
| ----------- | --------------------------------- | ---- |
| data        | 取值表达式                             | 是    |
| alias       | 别名，你可以重新定义显示名                     | 否    |
| converter   | 类型转换器全限定类名或expression表达式          | 否    |
| null-hidden | true时表示表达式取的值为null时隐藏该节点，默认为false | 否    |

###### d) object-virtual

该标签用于虚拟对象结构。详见[5.2.节](#chapter52)

```xml
<object-virtual alias=""></object-virtual>
```

| 属性    | 功能   | 是否必须 |
| ----- | ---- | ---- |
| alias | 别名   | 是    |

###### e) property-ignore

忽略属性，需结合`for-class`属性使用。详见[4.5.节](#chapter45)

```xml
<property-ignore name=""/>
```

| 属性   | 功能        | 是否必须 |
| ---- | --------- | ---- |
| name | 类中忽略的属性名称 | 是    |

###### f) extend-port

此标签位置作为子`<template>`的接入位置。详见[5.3.2节](#chapter532)

```xml
<extend-port port-name=""/>
```

| 属性        | 功能   | 是否必须 |
| --------- | ---- | ---- |
| port-name | 端口名称 | 是    |

##### **3.2.2.3. 拓展型标签**

###### a) property-date

该标签拓展于`<property>`，针对时间日期类型，使用`pattern`属性来格式化日期时间。详见[4.3.1.节](#chapter431)

| 拓展属性    | 功能                                  | 是否必须 |
| ------- | ----------------------------------- | ---- |
| pattern | 格式化模板字符串，不填默认为"yyyy-MM-dd HH:mm:ss" | 否    |

支持的时间日期类型：

- java.util.Date
- java.util.Calendar
- java.sql.Date
- java.sql.Time
- java.sql.Timestamp
- (JDK8) java.time.LocalDate
- (JDK8) java.time.LocalDateTime
- (JDK8) java.time.LocalTime
- (JDK8) java.time.Instant

###### b) property-unixtimestamp

该标签拓展于`<property>`，针对时间日期类型，可以将时间日期类型转化为unix时间戳格式显示。可支持的时间日期类型同`<property-date>`。详见[4.3.2.节](#chapter432)

###### c) property-boolean

该标签拓展于`<property>`，可以将数字类型（short、int、long）变为boolean型，非0值为true，0值为false。详见[4.3.3节](#chapter433)

## **4. 基本使用**

模型声明（以下各小节示例代码均使用这些模型实体类）：

```java
// 学生类 Student.java
@Data
public class Student {
    // 学生名称
    private String name;
    // 班级ID
    private int classId;
    // 出生日期
    private Date birthday;

    public Student(String name, int classId, String birthday) {
        this.name = name;
        this.classId = classId;
        if(birthday != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.birthday = dateFormat.parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```java
// 账户类 Account.java   一个学生对应有一个账户
@Data
@AllArgsConstructor
public class Account {
    private String username;
    private String password;
}
```

### **4.1. 简单输出模型对象Json**

```xml
<!-- /jsonview/jsonview-student.xml --> 
<!-- 忽略jsonview-configuration -->
<template-package namespace="jsonview-student">
  <template id="student-detail" data="student">
    <property data="name"/>
    <property data="classId"/>
    <property data="birthday"/>
  </template>
</template-package>
```

```java
// Application.java    main()
JsonviewFactory factory = new JsonviewFactory("/jsonview/jsonview-student.xml");
JsonProducer jsonProducer = factory.getJsonProducer();
DataModel dataModel = new HashDataModel();
Student peter = new Student("Peter", 1, "1995-01-01");
dataModel.putData("student", peter);
String json = jsonProducer.createJson(dataModel, "jsonview-student", "student-detail");
System.out.println(json);
```

执行结果：

```json
{"name":"Peter","classId":1,"birthday":"Sun Jan 01 00:00:00 CST 1995"}
```

### 4.2. 使用alias修改显示名称

```xml
<!-- /jsonview/jsonview-student.xml --> 
<!-- 忽略jsonview-configuration -->
<template-package namespace="jsonview-student">
  <template id="student-detail" data="student">
    <property data="name" alias="student_name"/>
    <property data="classId" alias="student_classId"/>
    <property data="birthday" alias="student_birthday"/>
  </template>
</template-package>
```

```json
{"student_name":"Peter","student_classId":1,"student_birthday":"Sun Jan 01 00:00:00 CST 1995"}
```

### **4.3. property扩展**

#### <a name="chapter431">**4.3.1 使用property-date格式化日期时间**</a>

该标签可以格式化时间。
把4.1.节代码

```xml
<property data="birthday"/>
```

替换为

```xml
<property-date data="birthday" pattern="yyyy-MM-dd"/>
```

运行结果：

```json
{"name":"Peter","classId":1,"birthday":"1995-01-01"}
```

#### <a name="chapter432">**4.3.2 使用property-unixtimestamp输出时间戳**</a>

该标签可以使日期时间类型转化成时间戳形式输出。

```java
dataModel.putData("datetime", LocalDateTime.of(2016, 1, 1, 0, 0, 0));
```

```json
{"datetime" : 1451577600}
```

#### <a name="chapter433">**4.3.3 使用property-boolean转换**</a>

该标签可以把非0数字转换成true，0转换成false

```java
DataModel dataModel = new HashDataModel();
dataModel.putData("number1", 1);
dataModel.putData("number2", 0);
```

```json
{"number1" : true, "number2" : false}
```

### **4.4. 使用null-hidden隐藏值为null的属性**

把4.1.节代码

```xml
<property data="birthday"/>
```

替换为

```xml
<property data="birthday" null-hidden="true"/>
```

student实例传入null的birthday值

```java
Student student = new Student("Peter", 1, null);
```

运行结果：

```json
{"name":"Peter","classId":1}
```

### <a name="chapter45">**4.5. 使用for-class输出模型对象Json**</a>

```xml
<template id="student-detail" data="student" for-class="test.Student" />
```

运行结果和4.1.节相同。
使用`property-ignore`忽略不需要的属性：

```xml
<template id="student-detail" data="student" for-class="test.Student">
  <property-ignore name="birthday" />
</template>
```

运行结果：

```json
{"name":"Peter","classId":1}
```

### 4.6. 简单输出数组模型Json

利用`array` 标签构造一个数组结构：

```xml
<template id="student-list">
  <array data="students">
    <property data="name"/>
    <property data="classId"/>
    <property-date data="birthday" pattern="yyyy-MM-dd"/>
  </array>
</template>
```

```java
Student peter = new Student("Peter", 1, "1995-01-01");
Student john = new Student("John", 1, "1996-5-20");
Student[] students = {peter, john};
dataModel.putData("students", students);
// isPretty参数设为true，开启json美化
String json = jsonProducer.createJson(dataModel, "jsonview-student", "student-list", true);
```

```json
{
  "students" : [ {
    "name" : "Peter",
    "classId" : 1,
    "birthday" : "1995-01-01"
  }, {
    "name" : "John",
    "classId" : 1,
    "birthday" : "1996-05-20"
  } ]
}
```

或者直接把data设定在`<template>` 标签上，Jsonview框架会自动识别data对应的数据是否是数组或List。

```xml
<template id="student-list" data="students">
  <property data="name"/>
  <property data="classId"/>
  <property-date data="birthday" pattern="yyyy-MM-dd"/>
</template>
```

```json
[ {
  "name" : "Peter",
  "classId" : 1,
  "birthday" : "1995-01-01"
}, {
  "name" : "John",
  "classId" : 1,
  "birthday" : "1996-05-20"
} ]
```

## **5. 高级功能**

### **5.1. Property的转换器**

`com.github.developframework.jsonview.core.converter.PropertyConverter`
接口可以对表达式选取的属性值进行自定义转换。

```java
@FunctionalInterface
public interface PropertyConverter<TARGET> {

    /**
     * 转化方法
     * @param source 源对象
     * @return 目标对象
     */
    TARGET convert(Object source);
}
```

- 泛型TARGET为转化后的类型。

例如将peter的name处理后输出：

```xml
<template id="student-detail" data="student">
  <property data="name" converter="nameConverter"/>
  <property data="classId"/>
  <property-date data="birthday" pattern="yyyy-MM-dd"/>
</template>
```

```java
dataModel.putData("student", peter);
// 这里采用JDK8 lambda写法
dataModel.putData("nameConverter", (PropertyConverter<String>) source -> "My name is " + source);
```

```json
{
  "name" : "My name is Peter",
  "classId" : 1,
  "birthday" : "1995-01-01"
}
```

`<property>` 系列标签的`converter` 属性可以填写Expression表达式，还可以填写`PropertyConverter` 的接口实现类全类名。

### <a name="chapter52">**5.2. 虚拟结构**</a>

使用`<object-virtual>`可以虚拟一个对象结构。
利用仅有的属性值，构造一个对象结构：

```xml
<template id="student-detail">
  <object-virtual alias="student">
    <property data="name"/>
    <property data="classId"/>
    <property-date data="birthday" pattern="yyyy-MM-dd"/>
  </object-virtual>
</template>
```

```java
dataModel.putData("name", "Peter");
dataModel.putData("classId", 1);
dataModel.putData("birthday", "1995-01-01");
```

```json
{
  "student" : {
    "name" : "Peter",
    "classId" : 1,
    "birthday" : "1995-01-01"
  }
}
```

### **5.3. 模块化**

#### <a name="chapter531">**5.3.1 引用**</a>

使用`<include>`标签引用其它`<template>`模板，从而可实现模块化设计，避免重复定义视图模板。

```xml
<template-package namespace="jsonview-student">
  <template id="student-list" data="students">
    <include id="student-detail" />
  </template>

  <template id="student-detail">
    <property data="name"/>
    <property data="classId"/>
    <property-date data="birthday" pattern="yyyy-MM-dd"/>
  </template>
</template-package>
```

#### <a name="chapter532">**5.3.2 继承**</a>

Jsonview框架的继承的概念，在`<template>`标签可以添加属性`extend`指定继承的template和继承的端口。继承的概念可以理解为反向include，调用子template视图，会优先从父template开始构造结构，当遇到匹配端口名的`<extend-port>`标签时才会构造子template视图。

**注意：**假如单独调用了有`<extend-port>`标签的父template视图或者端口没有与之对应的子template实现，则`<extend-port>`标签被忽略。

```xml
<template-package namespace="jsonview-student">
  <!-- 一个父视图模板  -->
  <template id="student-parent">
    <object-virtual alias="other">
      <property data="otherData" />
    </object-virtual>
    <!-- 子视图模板的内容将会插入到这个端口位置上 -->
    <extend-port port-name="my-port" />
  </template>

  <!-- 子视图模板  -->
  <template id="student-detail" extend="student-parent:my-port">
    <!-- 本模板内容将会插入到父视图模板的my-port端口位置上 -->
    <object data="student">
      <property data="name"/>
      <property data="classId"/>
      <property-date data="birthday" pattern="yyyy-MM-dd"/>
    </object>
  </template>
</template-package>
```

```java
Student peter = new Student("Peter", 1, "1995-01-01");
dataModel.putData("student", peter);
dataModel.putData("otherData", "I'm other data.");
// 这里调用的子视图模板
String json = jsonProducer.createJson(dataModel, "jsonview-student", "student-detail", true);
```

```json
{
  "other" : {
    "otherData" : "I'm other data."
  },
  "student" : {
    "name" : "Peter",
    "classId" : 1,
    "birthday" : "1995-01-01"
  }
}
```

`extend` 属性的写法为  namespace.templateId:portName  其中namespace可以省略，默认为当前命名空间下。

### **5.4. 链接与映射**

#### <a name="chapter541">**5.4.1. 一对一数组链接**</a>

使用`<object-one-to-one>` 和`<property-one-to-one>` 标签可以在数组间一对一链接对象。**该标签仅能在`<array>`下使用。**当`<object-one-to-one>` 和`<property-one-to-one>` 的data属性所指的数组和父`<array>`数组个数不相同时将会抛出`OneToOneSizeNotEqualException`。
例子：
假如每个学生实例都有一个账户实例，并且又都一对一对应了一个成绩值。

```xml
<template-package namespace="jsonview-student">

  <template id="student-list" data="students">
    <property data="name"/>
    <property data="classId"/>
    <property-date data="birthday" pattern="yyyy-MM-dd"/>
    <!-- 一对一对应accounts数组每项 -->
    <object-one-to-one data="#accounts" alias="account">
      <!-- 引用另一个命名空间的模板 -->
      <include id="account-detail" namespace="jsonview-account"/>
    </object-one-to-one>
    <!-- 一对一对应scores数组每项 -->
    <property-one-to-one data="#scores" alias="score" />
  </template>
</template-package>

<template-package namespace="jsonview-account">

  <template id="account-detail">
    <property data="username"/>
    <property data="password"/>
  </template>
</template-package>
```

```java
 Account peterAccount = new Account("peter's username", "peter's password");
Account johnAccount = new Account("john's username", "john's password");

Student[] students = {peter, john};
Account[] accounts = {peterAccount, johnAccount};
Integer[] scores = {95, 98};

dataModel.putData("students", students);
dataModel.putData("accounts", accounts);
dataModel.putData("scores", scores);
```

```json
[ {
  "name" : "Peter",
  "classId" : 1,
  "birthday" : "1995-01-01",
  "account" : {
    "username" : "peter's username",
    "password" : "peter's password"
  },
  "score" : 95
}, {
  "name" : "John",
  "classId" : 1,
  "birthday" : "1996-05-20",
  "account" : {
    "username" : "john's username",
    "password" : "john's password"
  },
  "score" : 98
} ]
```

### 5.5. 分支结构

#### 5.5.1. `<if>` `<else>`

可以使用`<if>` `<else>` 标签进行模块内容的取舍。`<else>` 标签可以不写，但必须紧跟`<if>` 后出现。

`<if>` 标签的`condition` 属性内容为接口`com.github.developframework.jsonview.core.dynamic.Condition` 的实现类。

```java
@FunctionalInterface
public interface Condition {

    /**
     * 判断条件
     * @param dataModel 数据模型
     * @param expression 当前位置的表达式
     * @return 判断结果
     */
    boolean verify(DataModel dataModel, Expression expression);
}
```

最简范例：

```xml
<template id="first-view">
  <if condition="myCondition">
    <property data="sayHello"/>
  </if>
  <else>
    <property data="sayBye"/>
  </else>
</template>
```

```java
dataModel.putData("sayHello", "Hello");
dataModel.putData("sayBye", "Bye");
dataModel.putData("myCondition", (Condition) (dm, expression) -> true);
```

```json
{"sayHello" : "Hello"}
```

当myCondition 接口返回false时

```json
{"sayBye" : "Bye"}
```

## **6. 日志**

Jsonview框架使用slf4j-api日志接口，提供内部日志打印功能。可以使用log4j或者logback打印日志。
以下示例使用logback
logback.xml

```xml
<configuration scan="true" scanPeriod="60 seconds" debug="false">
	<contextName>jsonview-log</contextName>
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<pattern>%d{HH:mm:ss.SSS} %-5level - %msg%n
			</pattern>
		</encoder>
	</appender>
	<logger name="com.github.developframework.jsonview" level="INFO" additivity="false">
		<appender-ref ref="STDOUT" />
	</logger>
</configuration>
```

项目启动日志：