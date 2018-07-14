## spring framework 的工程结构

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



## spring framework 的功能

- [Core technologies](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html): dependency injection, events, resources, i18n, validation, data binding, type conversion, SpEL, AOP.
- [Testing](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html): mock objects, TestContext framework, Spring MVC Test, `WebTestClient`.
- [Data Access](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/data-access.html): transactions, DAO support, JDBC, ORM, Marshalling XML.
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) and [Spring WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) web frameworks.
- [Integration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/integration.html): remoting, JMS, JCA, JMX, email, tasks, scheduling, cache.
- [Languages](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/languages.html): Kotlin, Groovy, dynamic languages.





# 面试题

## 什么是 Spring

## Spring 有哪些好处

## IOC 和 DI

## BeanFactory 和 ApplicationContext

## Spring Bean 的生命周期

## Spring Bean 的作用域

## 单例 Beans 是线程安全的么