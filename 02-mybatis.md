## Demo环境搭建

pom.xml

```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.4.1</version>
</dependency>
```

mybatis-config.xml

```xml 
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```
``` Java
@Test
public void loadResource() throws Exception{
	InputStream stream = ClassLoader.getSystemResourceAsStream("mybatis-config.xml");
	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	while(true){
		String line=reader.readLine();
		if(line==null)break;
		System.out.println(line);
	}
}

@Test
public void loadResource1() throws Exception{
  InputStream stream =Resources.getResourceAsStream("mybatis-config.xml");
  BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
  while(true){
    String line=reader.readLine();
    if(line==null)break;
    System.out.println(line);
  }
}
```
### 创建SqlSessionFactory

```xml
<configuration>
	<environments default="dev">
		<environment id="dev">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="org.h2.Driver" />
				<property name="url" value="jdbc:h2:~/test" />
				<property name="username" value="sa" />
				<property name="password" value="" />
			</dataSource>
		</environment>
	</environments>
</configuration>
```

```java
@Test
public void createSqlSessionFactory() throws Exception{
	InputStream conf = ClassLoader.getSystemResourceAsStream("mybatis-config.xml");
	SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(conf);
	SqlSession session = factory.openSession();
	System.out.println(session);
}
```
### 启动h2数据库

```xml 
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<version>1.4.196</version>
</dependency>
```
```java
public static void main(String[] args) throws Exception {
	Server h2 = Server.createWebServer().start();
	System.out.println(h2.getURL());
}
```
创建表并插入数据

![](img/mybatis-01.jpg)



### 查询单条记录

```java
public class Blog {
	private String tittle;
	private String content;
	//getter,setter,toString 
}

public interface BlogMapper {
	Blog selectBlog(String tittle);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="test.BlogMapper">
	<select id="selectBlog" resultType="test.Blog">select * from blog where tittle=#{tittle}</select>
</mapper>
```



```java
public static void main(String[] args) throws Exception {
  Server h2 = Server.createWebServer().start();
  InputStream stream = ClassLoader.getSystemResourceAsStream("mybatis-config.xml");
  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(stream);
  SqlSession session = sessionFactory.openSession();
  Blog one = session.selectOne("test.BlogMapper.selectBlog","hello");
  System.out.println(one); //Blog [tittle=hello, content=world]
  h2.stop();
}
```
### 使用@Select代替Mapper.xml

```java
public interface BlogMapper {
	@Select("select * from blog where tittle = #{tittle}")
	Blog selectBlog(String tittle);
}
```

```xml
<mappers>
	<!-- <mapper resource="mapper/BlogMapper.xml"/> -->
	<package name="test"/>
</mappers>
```
```java
public static void main(String[] args) throws Exception {
	Server h2 = Server.createWebServer().start();
	InputStream stream = ClassLoader.getSystemResourceAsStream("mybatis-config.xml");
	SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(stream);
	SqlSession session = sessionFactory.openSession();
	BlogMapper blogMapper = session.getMapper(BlogMapper.class);
	Blog blog = blogMapper.selectBlog("key1");
	System.out.println(blog); //Blog [tittle=key1, content=value1]
	h2.stop();
}
```

### 基于注解的 mybatis demo 

>  文件清单如下

```properties
│  pom.xml
│
└─src
    └─main
        ├─java
        │  └─com
        │      └─example
        │          └─ch1
        │                  App.java
        │                  User.java
        │                  UserMapper.java
        │
        └─resources
            └─ch1
                    create.sql
                    mybatis-config.xml
```

> pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.example</groupId>
	<artifactId>mybatis</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<dependencies>
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.4.1</version>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>1.4.196</version>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.16.18</version>
			<scope>provided</scope>
		</dependency>
	</dependencies>
</project>
```

App.java

```java
package com.example.ch1;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.tools.Server;

public class App {
	public static void main(String[] args) throws SQLException {
		// 启动 h2 数据库
		Server h2 = Server.createWebServer().start();
		// 加载 mybatis-config.xml 配置文件
		InputStream conf = ClassLoader.getSystemResourceAsStream("ch1/mybatis-config.xml");
		// 创建 SqlSessionFactory
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(conf);
		SqlSession session = factory.openSession();
		// 获取mapper
		UserMapper mapper = session.getMapper(UserMapper.class);
		// CRUD
		boolean addUser = mapper.addUser(new User("Tom" + Math.random(), "" + Math.random()));
		
		List<User> all = mapper.selectAll();
		all.forEach(System.out::println);
		
		session.commit();
		session.close();
		h2.stop();
	}
}
```

User.java

```java
package com.example.ch1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String username;
	private String password;
	
}
```

UserMapper

```java
package com.example.ch1;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Select("select * from user where username = #{username}")
	User selectUser(String username);
	
	@Insert("insert into user values(#{username},#{password})")
	boolean addUser(User user);
	
	@Select("select * from user")
	List<User> selectAll();
}
```

create.sql

```sql
drop table  if exists user; 
create table user(username varchar2,password varchar2);
insert into user values('Tom1','Tomx');
insert into user values('Tom2','Tomxx');
insert into user values('Tom3','Tomxxx');
insert into user values('Tom4','Tomxxxx');
insert into user values('Tom5','Tomxxxxx');
```

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<environments default="dev">
		<environment id="dev">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="org.h2.Driver" />
				<property name="url" value="jdbc:h2:file:~/mybatisTest;MODE=MYSQL;INIT=RUNSCRIPT FROM './src/main/resources/ch1/create.sql'" />
				<property name="username" value="sa" />
				<property name="password" value="" />
			</dataSource>
		</environment>
	</environments>
	<mappers>
		<!-- 使用注解代替mapper.xml时，需要指定扫描的包 -->
		<package name="com.example.ch1"/>
	</mappers>
</configuration>
```

测试：

```
运行App后，输出结果如下：
User(username=Tom1, password=Tomx)
User(username=Tom2, password=Tomxx)
User(username=Tom3, password=Tomxxx)
User(username=Tom4, password=Tomxxxx)
User(username=Tom5, password=Tomxxxxx)
User(username=Tom0.49938776319143174, password=0.7153057032136195)
```

记者采访一百岁高龄的老太，问她年纪大了之后有没有什么烦恼?

奶奶想了想答道：“我喜欢比自己年龄大的、成熟稳重的男生。可是他们现在都死光了，哎

## 面试题

####和$的区别

```#相当于一个参数占位符“?”，用来补全预编译语句。
# 相当于一个参数占位符“?”，用来补全预编译语句。它补全预编译语句时，可以理解为在此参数值两端加了单引号。
$ 是单纯的字符串拼接，拼接完成后才会对SQL进行编译、执行，所以性能较低，也无法复用。
但是在有些#{}无法胜任的地方，还是会需要${}来完成。比如当SQL中数据库表名为参数时，由于表名不能加单引号，如果使用#{}语法错误。这时候就需要使用${}来进行字符串拼接。
select * from #{tableName};
select * from 'my_user';
```

### PreparedStatement和Statement

```
PreparedStatement对象允许数据库预先编译SQL语句，数据库可以解析SQL并计算访问计划，而Statement不会

一个sql语句执行过程中，将经历这么几个步骤：
1、提交一个sql语句给数据库
2、数据库验证并解析SQL
3、数据库计算访问计划。
4、根据访问计划进行检索，返回数据。
```

