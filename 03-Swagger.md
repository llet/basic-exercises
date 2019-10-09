# Swagger

#### Swagger是什么

- Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化RESTful风格的Web服务。Swagger的目标是对REST API定义一个标准的和语言无关的接口。
- Swagger是一组开源项目，其中主要要项目如下
  - Swagger-ui：解析 JSON 或 YML 文件。

- Swagger-editor：这是在线编辑器，用于验证你的 YML/JSON 格式是否合法 。
- Swagger-codegen：一个模板驱动引擎，通过分析用户Swagger资源声明，这个工具可以为不同的平台生成客户端 SDK（比如 Java、JavaScript、Python 等）。这些客户端代码帮助开发者在一个规范平台中整合 API ，并且提供了更多健壮的实现，可能包含了多尺度、线程，和其他重要的代码。
- Swagger-core: 用于Java/Scala的的Swagger实现。与JAX-RS(Jersey、Resteasy、CXF...)、Servlets和Play框架进行集成。
- Swagger-js: 用于JavaScript的Swagger实现。

#### Swagger可以做什么

- Swagger可以生成一个交互性的API控制台，开发者可以用来快速学习API，同时也方便测试人员了解API。 

- Swagger支持OpenApi规范生成代码，生成的客户端和服务器端框架代码可以加速开发和测试速度。

- Swagger文件可以用不同的编程语言的代码注释中自动生成。



参考： [Swagger UI/Editor/Codegen实战](https://my.oschina.net/guol/blog/993689) 