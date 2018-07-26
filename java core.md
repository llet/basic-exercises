### DOM

# java基础

## java语言相关

### 类的实例化顺序

> 1. 赋值父类静态变量和执行静态代码块
> 2. 赋值子类静态变量和执行静态代码块
> 3. 执行父类构造代码块和构造方法
> 4. 执行子类构造代码块和构造方法

### 面向对象特性

### 多态

### 抽象类和接口

## 集合框架

### 常用集合有哪些

> 集合框架有两大接口:Collection  和 Map 
> Collection下有 List可重复集合 和Set不可重复集合
> List的实现有:数组结构的ArrayList,Vector和Stack,链表结构的LinkedList
> Set的实现有:HashSet,LinkedHashSet,TreeSet
> Map的实现有:HashMap,LinkedHashMap,TreeMap,Hashtable



### ArrayList 原理

> ArrayList 是数组实现的.它有一个重要的成员变量 Object[] elementData;
> 插入元素的时候,找到索引所在的位置插入相应的元素,并把当前位置后的元素依次向后移动一位.
> 查询的时候可以直接根据索引获取到对应的元素.
>
> ArrayList 初始容量为10, oldCapacity + (oldCapacity >> 1) 大概1.5倍增长
>
> transient Object[] 是实际存放数据的地方,transient 是为了节省内存做的优化, 
> ArrayList 中自定义了序列化的规则,  
> 只对Object[] 中实际存在的对象进行序列化,而不是整个数组,这样节省了内存.



### Vector 原理

> Vector 与ArrayList 的实现原理一样,都是使用数组来保存数据,
> 不同的是Vector的增删改查操作都是线程同步的。使用了synchronized关键字
> 保证了线程同步.



### LinkedList 原理

> LinkedList 是基于双向链表实现的, 它有一个内部类 Node实现了双向的链表的数据结构,Node 中包含了上一个节点和下一个节点的引用
> java8 中LinkedList 有这三个成员变量:Node first ；Node last ；int size ；
>
> 假如size为10,现在需要在索引为3的位置插入一个元素,则从 first 开始,递归向后查找到第四个元素后,
> 在这个Node前面插入新 Node,  
> 然后维护好这两个Node的指针.如果是在索引为8的位置插入 则从last开始,由后向前进行.
>
> 查询index为3的元素时,从first开始,由第一个节点向后查询到第四个元素返回.



### HashMap 原理

> HashMap 是数组和链表的结合体，本质上是一个数组，数组中保存的是具有链表数据结构的Entry。
> HashMap 的一些私有变量：int capacity；float loadFactor; Entry[] table;int size;int threshold;
>
> put操作的实现：HashMap 的 hash 方法会根据 key 的 hashCode 生成一个索引，如果 table 数组在该位置没有元素，就 new 一个 Entry 保存在该位置，如果已经存在元素且两者的 key 不相等，就将新元素保存在链表头部，如果已经存在元素且两者的 key 相等，就将新元素覆盖旧元素。在JDK8中，当链表长度达到8，会转化成红黑树
>
> 读取的实现：hash 方法会根据 key 的 hashCode 生成一个索引，再取出table数组中的索引处的Entry，然后返回该key对应的value。
>
> HashMap 的扩容：capacity是数组的长度，默认是大小为16，loadFactor 是装载因子，默认0.75，当元素个数，size表示HashMap中存放KV的数量 。threshold 是扩容临界值。当size > threshold 时会进行扩容。扩容会创建一个原来2倍大小的新数组，然后将原哈希表中的所有数据移动到新的哈希表中，相当于对原哈希表中所有的数据重新做了一个put操作。所以性能消耗很大
>
> 链的产生：新的Entry对象添加到 table 中时，新添加的 Entry 对象将持有对原有 Entry 对象的引用，形成Entry链。
>
> Hash 冲突：当试图将两个key的hashCode相同时，会产生hash冲突，hashMap是通过链表的形式解决hash冲突。后添加的 Entry 对象将持有对原有 Entry 对象的引用，形成Entry链。这是哈希算法中解决冲突的一种方法,叫链地址法



### **LinkedHashMap 原理**

> LinkedHashMap 是HashMap的子类，拥有HashMap的所有特性。并自己维护了一个双向链表。类里有两个成员变量 Entry head ;Entry  tail, 分别指向双向链表的表头、表尾。  accessOrder 默认 false ,表示 基于插入顺序 
>
> put 操作: LinkedHashMap并没有重写任何put方法。但是其重写了构建新节点的newNode()方法. 在每次构建新节点时，将新节点链接在内部双向链表的尾部。
>
> get操作：LinkedHashMap的get()方法会改变数据链表，LinkedHashMap具有可预知的迭代顺序：插入顺序、访问顺序。默认是插入顺序排序，如果指定按访问顺序排序，那么调用get方法后会将这次访问的元素放到链表的尾部，不断访问可以形成按访问顺序形成的列表.

```java
Map<String, String> map = new LinkedHashMap<String, String>(16, 0.75f, true);  
for (int i = 0; i < 10; i++) {  
    map.put("key" + i, "value" + i);  
}
map.get("key" + 3);  
for (String value : map.keySet()) {  
    System.out.println(value); // key3:value3 被放在了最后
}
```

### **TreeMap 原理**

> TreeMap 初始化的时候会初始化下列参数，第一个Comparator是可以自己定义实现的一个比较的实现，默认为Null,那么默认的比较方式就是compare方法。Entry root;默认为Null。其中Entry内部维护了left,right,parent,color  其中color默认是black。https://www.cnblogs.com/daoluanxiaozi/p/3340382.html



### HashSet 原理

> HashSet 聚合了 一个HashMap ，使用HashMap的key来保存数据， 它所有的方法都是调用HashMap对应的方法来实现的



### Arrays.sort 和 Collections.sort怎么实现的？

> Arrays.sort对于基本数据类型使用的是快速排序，对 对象使用的是归并排序 ，两者的时间复杂度都是n*logn,但合并排序需要额外的n个空间 

LinkedHashMap的应用场景

hashtable和hashmap的区别及实现原理

hashmap会问到数组索引

hash碰撞怎么解决 

arraylist和linkedlist区别及实现原理

TreeMap的实现原理

concurrenthashmap具体实现及其原理，jdk8下的改版

如果让你实现一个并发安全的链表，你会怎么做

简述ConcurrentLinkedQueue和LinkedBlockingQueue的用处和不同之处

## 动态代理

### JDK 动态代理

JDK动态代理有一定的使用限制, 只能对实现了接口的类进行代理, 它实现方式是 通过 Proxy类 的一个静态方法来产生一个代理对象, 这个过程需要指定代理对象, 指定类装载器, 指定接口, 指定回调处理.   在回调中调用目标对象的方法, 并增加横切业务. 在使用生成的代理类时, 需要把这个代理对象强转成指定的接口类型, 然后完成调用. 

### CGLIB 动态代理

根据目标对象的字节码 动态生成一个子类，子类重写父类的非final方法。在子类中拦截所有父类方法的调用，并增加横切业务。代码实现过程: 创建一个enhancer ,设置父类和MethodInterceptor, 使用enhancer 生成代理对象, 

### 反射的原理

一般的java程序在运行之前, 所有的字节码都需要加载到内存中去, 而java的反射指的是在程序运行时去加载字节码文件,  

### 创建类实例的三种方式

### Class.forName和ClassLoader区别

classLoader只是将字节码加载到jvm中, class.forName() 除了将字节码加载到jvm中之外，还会对类进行解释, 并执行静态代码块

## 泛型

## IO/NIO

reader.readLine() 和 inputStream.read(), outputStream.flush() 的坑 

> reader.readLine() 在遇到换行符之前会一直读 , 这就会发生阻塞,  直到遇到换行符的时候才会返回读取结果 ,且 换行符并不返回, 
>
> inputStream.read() 方法执行时如果遇到文件末尾会返回-1,而socket通信时，只要客户端保存连接，服务端会一直等待客户端输入. 
>
> 解决办法:  在最后一次读取时由于流里所剩的字节数小于b的长度，流就认为到了流的末尾。如果为整数的话阻塞原因同上。
>
> outputStream.flush()  的作用是清空缓冲区, 并不保证网络传输.  

## 序列化

### UID的作用

反序列化时的UID必须与序列化前的UID相同,否则会序列化失败

### java默认的序列化的缺点

只支持java语言, 性能比较低 

### 其他序列化方案

json, xml, kryo, protocol buf, hesson, thrift, messagepack

### 序列化的应用

深拷贝对象

```java
public Student testClone()  throws Exception{
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream outputStream2 = new ObjectOutputStream(outputStream);
    outputStream2.writeObject(this);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    ObjectInputStream inputStream2 = new ObjectInputStream(inputStream);
    Object object = inputStream2.readObject();
    return (Student) object;
}
```



## 多线程

### 线程状态？

> java中有一个枚举类描述的线程状态，一共6个：new,runable,blocked,timed_waiting,waiting,terminated
>
> 一般认为线程有5个状态，首先是新建状态，调用这个线程的start方法后线程进入就绪状态，CPU调度到这个线程后进入运行状态，遇到IO、网络连接、或者调用sleep、wait 时会进入阻塞状态，需要重新等待CPU的调度，最后是终止状态。（我们认为IO、网络连接不消耗CPU ）

### 同步方法与同步块的区别

> 执行同步方法需要获得当前实例的监视器，执行同步代码块需要获取指定实例的监视器
>
> 同步的实例方法执行时需要获取类对象的监视器，类似于在同步代码块中使用this作为同步对象，这种情况下， 一个实例的某个非静态同步方法不能同时在两个线程中运行，
> 如果这个实例有两个非静态同步方法 a()和 b(),这两个方法也不能同时在两个线程中运行。
> 静态同步方法锁定的是实例的类对象，同一个对象的多个实例,他们的静态同步方法不能在两个线程中同时运行。
> 对于锁定相同对象的方法/代码块不能同时运行在多个线程中。

### 如何让多个线程按顺序执行

> 通过join方法来实现，主线程中启动了一个子线程，主线程调用子线程的join方法后，子线程完成后主线程才继续执行。也可 以通过线程数为1的线程池来实现.

```java
ExectorService excutor =  Excutors.newSingleThreadExcutor();
excutor.execte(thread1);
excutor.execte(thread2);
excutor.shuntdow();
```

### volatile, synchronized, Lock

> java内存分为工作内存和主存，工作内存是线程私有的，当一个线程对volatile变量的进行修改时，会立即从工作内存刷新到主存，并且其他线程对volatile变量的读会读主存中的值。主内存主要包括本地方法区和堆，工作内存主要包括栈、本地方法栈和程序计数器
>
> 当一个线程执行 某个对象的 synchronized 方法的时候，会先获取这个对象的监视器 Monitor，之后才能执行这个方法，如果获取不到监视器，则会阻塞并进入到一个同步队列等待获取监视器，其他线程释放这个监视器时会通知这个队列。当线程在synchronized 方法中又执行了这个对象的其他的synchronized  方法，则这个线程需要重新获取监视器，这个时候不会阻塞而是对Monitor的计数器加1。
>
> Lock是一个接口，它有3个实现类，ReentrantLock，ReadLock，WriteLock，其中后两个是ReentrantReadWriteLock的内部类，Lock 实例以及 Condition 实例可以用来阻塞、唤醒线程，达到控制线程的目的。

### 线程间如何通信

> 1. volatile 共享内存 
> 2. wait/notify 消息传递 

### 完成线程同步的方式

> 1. 使用带有synchronized关键字的同步方法或者同步代码块
> 2. 使用 java.util.concurrent.lock Lock对象。

### 什么是死锁

> 两个线程都在等待对方释放锁之后才能继续往下执行，就发生了死锁, 一般发生在两段同步代码块中



线程池的种类、区别和使用场景

分析线程池的实现原理和线程的调度过程

线程池如何调优

线程池的最大线程数目根据什么确定

volatile的语义，它修饰的变量一定线程安全吗

ThreadLocal用过么，原理是什么，用的时候要注意什么

Synchronized和Lock的区别

synchronized 的原理，什么是自旋锁，偏向锁，轻量级锁，什么叫可重入锁，什么叫公平锁和非公平锁

用过哪些原子类，他们的参数以及原理是什么

cas是什么，他会产生什么问题（ABA问题的解决，如加入修改次数、版本号）

简述AQS的实现原理

countdowlatch 和 cyclicbarrier的用法，以及相互之间的差别?

concurrent包中使用过哪些类？分别说说使用在什么场景？为什么要使用？

LockSupport工具

Condition 接口及其实现原理

Fork/Join框架的理解

分段锁的原理,锁力度减小的思考

## java8

 parallelStream 的理解

## 常用类及方法

### String, StringBuilder,  StringBuffer

> 三者都是使用char[] 的数组保存数据的 ,都是CharSequence 接口的实现类
>
> String中的 concat, replace 等方法不对char[]数组进行操作, 它最终返回一个新的String对象, 所以原String对象一但创建好后它的值将不会改变(因为没有改变的入口)
>
> StringBuilder 的append,delete等方法是对当前对象进行操作 并返回当前对象
>
> StringBuffer 的append 等方法都有 synchronized 关键字修饰

### final,finally,finalize

> final修饰类，表示类不能被继承,修饰方法,表示方法不能被重写,修饰成员变量,表示基本数据类型的变量是不可改变或者引用类型的引用不可改变,引用对象中的内容可以改变
>
> finally 是 try catch代码块的组成部分, 不管有没有异常发生finally 语句块都将被执行.
>
> finalize 是Object中的一个方法, 这个方法一般在GC线程中被调用,一般用来做关闭连接、关闭文件等释放资源的操作

### throw,throws,throwable

> throw 用在方法体内,表示抛出异常
>
> throws 用在方法声明后面,表示再抛出异常,由调用这个方法的上一级方法中的语句来处理,出现异常的一种可能性
>
> Throwable 是 Java 语言中所有错误或异常的超类

### sleep,wait

cloneable接口实现原理，浅拷贝or深拷贝

> Object a=new Object();Object b;b=a;这种形式的代码复制的是引用,
> a和b对象仍然指向了同一个对象
> 浅拷贝



quartz 和 timer对比

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