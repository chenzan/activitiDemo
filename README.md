# activitiDemo
## 内容
- SpringBoot集成activiti 6.0.0
- SpringBoot集成thymeleaf和mybatis
- 主从数据源切换（两个版本）一是利用Spring提供的AbstractRoutingDataSource，二是使用自定义数据源切换类
### 模块
```
actdemo
├── mybatisGenerator --mybatis生成程序
└── web --SpringBoot项目
```
#### 集成activiti6.0.0
1.pom文件中添加依赖
```
   <dependency>
        <groupId>org.activiti</groupId>
        <artifactId>activiti-spring-boot-starter-basic</artifactId>
        <version>6.0.0</version>
   </dependency>
```
2.在application.yml中添加配置信息
```
spring:
    activiti:
        async-executor-activate: false
        process-definition-location-prefix: classpath:/processes/*/ #spring自动部署activiti读取的目录
        check-process-definitions: true #是否检查上面配置的目录，自动部署需要为true但必须保证目录中有正确的bpmn文件
```
* 此配置对应org.activiti.spring.boot.ActivitiProperties配置类，
* DataSourceProcessEngineAutoConfiguration会进行自动配置，注入DataSource和相关服务
* Spring可以对activiti进行自动部署，需要设置check-process-definitions为true

3.此时启动项目报错(java.lang.ArrayStoreException: sun.reflect.annotation.TypeNotPresentExceptionProxy)
* 在Springboot启动类添加@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

4.至此activiti配置完成
* com.act.demo.service.impl.ProcessServiceImpl封装了相关方法，获取流程、执行流程、查询历史等

5.idea支持
* 下载插件actiBPM,如果idea无法下载插件修改
  'File | Settings | Appearance & Behavior | System Settings | Updates'取消Use secure connection选项应用后再次下载。

6.actiBPM插件流程图中文显示乱码问题

* 找到idea安装目录bin目录下，idea.exe.vmoptions或者idea64.exe.vmoptions(64位)
用编辑器打开，在文件末尾添加 -Dfile.encoding=UTF-8 ，然后重启idea，再打开流程图就会发现中文已经可以正常显示了。
* 此时spring自动部署的流程图中文仍然显示乱码，需要在processEngineConfiguration添加中文支持等。
* 本项目通过以下代码获取流程图片避免显示乱码
```
ProcessDiagramGenerator processDiagramGenerator = processEngine
                                                               .getProcessEngineConfiguration()
                                                               .getProcessDiagramGenerator();
processDiagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(), new ArrayList<>(), 
                                        "宋体", "微软雅黑", "黑体", null, 2.0);
InputStream is = processDiagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(), 
                                        new ArrayList<>(), "宋体", "微软雅黑", "黑体", null, 2.0)
```
####thymeleaf
* 添加公用的文件common_css.html
```
<th:block th:fragment="common_css" xmlns:th="http://www.w3.org/1999/xhtml">
    <link rel="stylesheet" th:href="@{/bootstrap/css/bootstrap.min.css}" type='text/css' media='screen'>
    <link rel="stylesheet" th:href="@{/css/font-awesome.css}" type='text/css' media='screen'>
    <link rel="stylesheet" th:href="@{/css/themify-icons.css}" type='text/css' media='screen'>
    <link rel="stylesheet" th:href="@{/css/simple-line-icons.css}" type='text/css' media='screen'>
    <link rel="stylesheet" th:href="@{/css/animate.min.css}" type='text/css' media='screen'>
    <link rel="stylesheet" th:href="@{/plugins/toastr/toastr.min.css}" type='text/css' media='screen'>
    <style>
        .table {
            word-wrap: break-word;
            table-layout: fixed;
        }
    </style>
</th:block>
```
* 添加公用的文件common_css.html
```
<th:block th:fragment="common_js" xmlns:th="http://www.w3.org/1999/xhtml">
    <script th:src="@{/js/jquery.min.js}"></script>
    <script th:src="@{/js/plugins/jquery.form.js}"></script>
    <script th:src="@{/bootstrap/js/bootstrap.js}"></script>
    <script th:src="@{/js/plugins/jquery.slimscroll.min.js}"></script>
    <script th:src="@{/plugins/toastr/toastr.min.js}"></script>
    <script th:src="@{/js/toast.js}"></script>
    <script th:src="@{/js/ajaxUtil.js}"></script>
</th:block>
```
* 页面引用
```
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" th:href="@{/css/login/style.css}" type="text/css">
    <link th:replace="/commons/common_css :: common_css">
    <script th:replace="/commons/common_js :: common_js"></script>
</head>
```
#### 多数据源切换
根据注解和方法名称进行切换
#####利用Spring的AbstractRoutingDataSource动态数据源切换
* 使用DataSourceSwitch，DataSourceSwitchAspect，DbContextHolder，RoutingDataSource
* 需要在WebMvcConfig中注入RoutingDataSource为dataSource
#####自定义可切换数据源
* 使用DataSourceSwitch，DataSourceSwitchAspect2，SwitcherDataSource





