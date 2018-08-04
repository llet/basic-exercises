### spring framework 的工程结构

```xml
<group>org.springframework</group>
<artifactId>spring</artifactId>
<modules> 
	<module>spring-aop</module>
    <module>spring-aspects</module>
    <module>spring-beans</module>
    <module>spring-context</module>
    <module>spring-context-support</module>
    <module>spring-context-indexer</module>
    <module>spring-core</module>
    <module>spring-expression</module>
    <module>spring-instrument</module>
    <module>spring-jcl</module>
    <module>spring-jdbc</module>
    <module>spring-jms</module>
    <module>spring-messaging</module>
    <module>spring-orm</module>
    <module>spring-oxm</module>
    <module>spring-test</module>
    <module>spring-tx</module>
    <module>spring-web</module>
    <module>spring-webmvc</module>
    <module>spring-webflux</module>
    <module>spring-websocket</module>
    <module>spring-framework-bom</module>
</modules>
```



### spring framework 的功能

- [Core technologies](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html): dependency injection, events, resources, i18n, validation, data binding, type conversion, SpEL, AOP.
- [Testing](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html): mock objects, TestContext framework, Spring MVC Test, `WebTestClient`.
- [Data Access](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/data-access.html): transactions, DAO support, JDBC, ORM, Marshalling XML.
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) and [Spring WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) web frameworks.
- [Integration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/integration.html): remoting, JMS, JCA, JMX, email, tasks, scheduling, cache.
- [Languages](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html): Kotlin, Groovy, dynamic languages.



## 面试题

### 谈谈spring

spring的核心功能有：IOC 容器（管理对象的生命周期、提供单例模式支持）、事件处理、资源访问、国际化、数据验证、数据绑定、类型转换、SpEl表达式、AOP（利用它很容易实现如权限拦截，运行期监控等功能）；

提供的众多服务，如事务管理，消息服务等 

容器提供了众多的辅助类

spring对于主流的应用框架提供了集成支持，如hibernate，JPA，Struts等 

独立于各种应用服务器 
spring的DI机制降低了业务对象替换的复杂性 IOC 和 DI

### BeanFactory 和 ApplicationContext

### Spring Bean 的生命周期

### Spring Bean 的作用域

### 单例 Beans 是线程安全的么

### 常用的设计模式

没必要把每一个细节都弄明白，当今社会讲究高效，不必重复造轮子。



### EL表达式

\#{} 支持字面量赋值, 数学运算, 



### 事务管理

jdbc的事务管理过程是 获取连接, 开启事务, 业务完成提交事务, 业务失败回滚事务. 

spring的事务管理有两种实现方式,一种是编程式事务,另一种是声明式事务, 编程式事务是指将事务的管理代码嵌入到业务代码中来控制事务的提交和回滚. 另一种是声明式事务.  可以通过AOP的方式来实现声明式事务管理. 

### 事务的特性

原子性：同一个事务包含多个操作 这多个操作是一个整理。一致性：隔离性：多个事务之间互不影响，一个事务不会读到另一个事务中未提交的数据；持久性：事务提交后数据将被永久保存；

### 事务的传播行为

事务的传播行为要对方法来声明；事务的传播行为有：

1. 加入当前事务，如果当前没有事务就新建事务。常用
2. 加入当前事务，如果当前没有事务就以非事务方式执行。
3. 加入当前事务，如果当前没有事务，抛出异常。
4. 不支持事务，在事务中时，抛出异常。
5. 挂起当前事务，另起事务。常用
6. 挂起当前事务，以非事务方式执行操作

注意：Spring的事务方法不能调用同一个类里面的另外一个事务方法，必须调用外面的类去做事物操作. 在一个Service内部，事务方法之间的嵌套调用，普通方法和事务方法之间的嵌套调用，都不会开启新的事务.是因为spring采用动态代理机制来实现事务控制，而动态代理最终都是要调用原始对象的，而原始对象在去调用方法时，是不会再触发代理了！

### 事务的隔离级别

常用的事务隔离级别是读已提交，此外还有读未提交，可以重复读，序列化 四种

### AOP的实现原理

spring AOP

### 通知有哪些分类

前置通知，后置通知，异常通知，返回通知，环绕通知；其中环绕通知主

### BeanFactory和FactoryBean

### CGlib

### RMI与代理模式

### 事务隔离级别

### 非单例注、循环注入

### controller是单例还是多例，怎么保证并发的安全

