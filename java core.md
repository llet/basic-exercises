### DOM

### 多线程

### 注解/反射

#### 元注解

> Retention  Target  Inherited  Documented  Native  Repeatable
>
> RetentionPolicy  ElementType

```java
@Retention(RetentionPolicy.RUNTIME) //RetentionPolicy value(); 保留到运行时
@Target({ElementType.FIELD,ElementType.METHOD}) //ElementType[] value(); 标记在字段/方法上
@Inherited //可以被子类继承
@Documented //将此注解包含在Javadoc中
@interface Value{
}
```

#### Annotation Processor 

定义了注解，必须有配套的注解处理器，通常都是通过Class对象配合反射机制来处理；网上和各种教科书中很多例子。

##### Runtime Annotation 

在运行时通过反射机制运行处理的注解

##### Compile time Annotation 

在编译时处理的注解。它以Java代码或者编译过的字节码作为输入，生成文件（通常是`.java`文件）作为输出。这意味着可以生成Java代码！

##### 自定义处理器

每一个处理器都是继承于`AbstractProcessor`

```java
public interface Processor {

    Set<String> getSupportedOptions();
 
    /**
    这里你必须指定，这个注解处理器是注册给哪个注解的。
    可以用这个注解代替 @SupportedAnnotationTypes({})
    */
    Set<String> getSupportedAnnotationTypes();
 
    /**
    用来指定你使用的Java版本。默认JDK6
    可以用这个注解代替 @SupportedSourceVersion(SourceVersion.latestSupported())
    */
    SourceVersion getSupportedSourceVersion();
    
	/**
	每一个注解处理器类都必须有一个空的构造函数。
	然而，这里有一个特殊的init()方法，它会被注解处理工具调用，并输入ProcessingEnviroment参数。
	ProcessingEnviroment提供很多有用的工具类Elements, Types和Filer。
	*/
    void init(ProcessingEnvironment processingEnv);
  
    /**
    这相当于每个处理器的主函数main()。你在这里写你的扫描、评估和处理注解的代码，以及生成Java文件。
    */
    boolean process(Set<? extends TypeElement> annotations,RoundEnvironment roundEnv);
 
    Iterable<? extends Completion> getCompletions
    (Element element,AnnotationMirror annotation,ExecutableElement member,String userText);
}

```

##### 注册处理器

文件:` META-INF/services/javax.annotation.processing.Processor`

```
com.example.MyProcessor
com.foo.OtherProcessor
net.blabla.SpecialProcessor
```

将上面的文件打包到jar文件中, javac会自动检查和读取`javax.annotation.processing.Processor`中的内容,并且注册`MyProcessor`作为注解处理器 

#### 参考

<https://race604.com/annotation-processing/>

<http://ifeve.com/java-8-features-tutorial/>

<http://www.infoq.com/cn/articles/Type-Annotations-in-Java-8>

### 动态代理

```java
public class Test {
	public static void main(String[] args) {
		HelloImpl impl = new HelloImpl();
		Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
				new Class<?>[] { Hello.class, Print.class }, (proxy1, method, params) -> {
					log("------------------before------------------");
					method.invoke(impl, params);
					log("------------------after-------------------");
					return Void.TYPE;
				});
		log("1 查看代理类的信息");
		log(proxy.getClass());//生成了一个  $Proxy0.class 文件
		log(proxy.getClass().getSuperclass()); //对应 java.lang.reflect.Proxy.class 文件
		log(proxy.getClass().getSuperclass().getSuperclass());
		log("2 实现陈接口有:");
		Stream.of(proxy.getClass().getInterfaces()).forEach(System.out::println);
		log("3 转换成 Hello");
		Hello hello=(Hello)proxy;
		hello.hello("world");
		
		log("4 转换成  Print");
		Print print=(Print)proxy;
		print.print("prety");
	}
	private static void log(Object obj) {System.out.println(obj);}
}

class HelloImpl implements Hello, Print {
	@Override public void hello(String str) {log("HelloImpl: " + str);}
	@Override public void print(String str) {log("HelloImpl: " + str);}
    private static void log(Object obj) {System.out.println(obj);}
}
interface Hello { void hello(String str);}
interface Print { void print(String str);}
```



### Socket

### IO

### NIO

### Date API

### Lambda

### concurrent

### C3P0

com.mchange.v2.c3p0.ComboPooledDataSource

### Apache Commons DBCP

org.apache.commons.dbcp.BasicDataSource

### BoneCP

com.jolbox.bonecp.BoneCPDataSource

### spring jdbc

org.springframework.jdbc.datasource.DriverManagerDataSource