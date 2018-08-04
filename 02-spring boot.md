## spring boot 的工程结构

```xml
<artifactId>spring-boot-build</artifactId>	
<modules>
    <module>spring-boot-project</module><!--packaging:pom-->
    <module>spring-boot-samples-invoker</module><!--packaging:pom-->
    <module>spring-boot-tests</module><!--packaging:pom-->
</modules>
```

```xml
<artifactId>spring-boot-project</artifactId>
<modules>
    <module>spring-boot-dependencies</module><!--packaging:pom-->
    <module>spring-boot-parent</module><!--packaging:pom-->
    <module>spring-boot-starters</module><!--packaging:pom-->    
    <module>spring-boot-autoconfigure</module>
    <module>spring-boot</module>
    <module>spring-boot-actuator</module>
    <module>spring-boot-actuator-autoconfigure</module>
    <module>spring-boot-devtools</module>
    <module>spring-boot-properties-migrator</module>
    <module>spring-boot-test</module>
    <module>spring-boot-test-autoconfigure</module>
    <module>spring-boot-tools</module>
    <module>spring-boot-cli</module>
    <module>spring-boot-docs</module>
</modules>	
```

```xml
<artifactId>spring-boot-starters</artifactId>
<modules><!--以下所有模块都是 packaging:pom--> 
    <module>spring-boot-starter</module>
    <module>spring-boot-starter-activemq</module>
    <module>spring-boot-starter-amqp</module>
    <module>spring-boot-starter-aop</module>
    <module>spring-boot-starter-artemis</module>
    <module>spring-boot-starter-batch</module>
    <module>spring-boot-starter-cache</module>
    <module>spring-boot-starter-cloud-connectors</module>
    <module>spring-boot-starter-data-cassandra</module>
    <module>spring-boot-starter-data-cassandra-reactive</module>
    <module>spring-boot-starter-data-couchbase</module>
    <module>spring-boot-starter-data-couchbase-reactive</module>
    <module>spring-boot-starter-data-elasticsearch</module>
    <module>spring-boot-starter-data-jpa</module>
    <module>spring-boot-starter-data-ldap</module>
    <module>spring-boot-starter-data-mongodb</module>
    <module>spring-boot-starter-data-mongodb-reactive</module>
    <module>spring-boot-starter-data-neo4j</module>
    <module>spring-boot-starter-data-redis</module>
    <module>spring-boot-starter-data-redis-reactive</module>
    <module>spring-boot-starter-data-rest</module>
    <module>spring-boot-starter-data-solr</module>
    <module>spring-boot-starter-freemarker</module>
    <module>spring-boot-starter-groovy-templates</module>
    <module>spring-boot-starter-hateoas</module>
    <module>spring-boot-starter-integration</module>
    <module>spring-boot-starter-jdbc</module>
    <module>spring-boot-starter-jersey</module>
    <module>spring-boot-starter-jetty</module>
    <module>spring-boot-starter-jooq</module>
    <module>spring-boot-starter-json</module>
    <module>spring-boot-starter-jta-atomikos</module>
    <module>spring-boot-starter-jta-bitronix</module>
    <module>spring-boot-starter-jta-narayana</module>
    <module>spring-boot-starter-logging</module>
    <module>spring-boot-starter-log4j2</module>
    <module>spring-boot-starter-mail</module>
    <module>spring-boot-starter-mustache</module>
    <module>spring-boot-starter-actuator</module>
    <module>spring-boot-starter-parent</module>
    <module>spring-boot-starter-quartz</module>
    <module>spring-boot-starter-reactor-netty</module>
    <module>spring-boot-starter-security</module>
    <module>spring-boot-starter-test</module>
    <module>spring-boot-starter-thymeleaf</module>
    <module>spring-boot-starter-tomcat</module>
    <module>spring-boot-starter-undertow</module>
    <module>spring-boot-starter-validation</module>
    <module>spring-boot-starter-web</module>
    <module>spring-boot-starter-webflux</module>
    <module>spring-boot-starter-websocket</module>
    <module>spring-boot-starter-web-services</module>
</modules>
```


