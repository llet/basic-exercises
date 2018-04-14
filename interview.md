[TOC]

# java基础

## 编译和运行

foreach和while的区别

父类与子类的静态代码构造方法和普通方法的执行顺序？

内部类与外部类？

类的实例化顺序

```
1. 赋值父类静态变量和执行静态代码块
2. 赋值子类静态变量和执行静态代码块
3. 执行父类构造代码块和构造方法
4. 执行子类构造代码块和构造方法
```

说一说你对环境变量classpath的理解？如果一个类不在classpath下，为什么会抛出ClassNotFoundException异常，  
如果在不改变这个类路径的前期下，怎样才能正确加载这个类？

## 面向对象

多态的

抽象类和接口



## 集合框架

### 常用集合框架有哪些?

> 集合框架有两大接口:Collection  和 Map 
> Collection下有 List可重复集合 和Set不可重复集合
> List的实现有:数组结构的ArrayList,Vector和Stack,链表结构的LinkedList
> Set的实现有:HashSet,LinkedHashSet,TreeSet
> Map的实现有:HashMap,LinkedHashMap,TreeMap,Hashtable



### ArrayList 的实现原理

> ArrayList 是数组实现的.它有一个重要的成员变量 Object[] elementData;
> 插入元素的时候,找到索引所在的位置插入相应的元素,并把当前位置后的元素依次向后移动一位.
> 查询的时候可以直接根据索引获取到对应的元素.
>
> ArrayList 初始容量为10, oldCapacity + (oldCapacity >> 1) 大概1.5倍增长
>
> transient Object[] 是实际存放数据的地方,transient 是为了节省内存做的优化, 
> ArrayList 中自定义了序列化的规则,  
> 只对Object[] 中实际存在的对象进行序列化,而不是整个数组,这样节省了内存.



### Vector的实现原理

> Vector 与ArrayList 的实现原理一样,都是使用数组来实现的集合,
> 不同的是Vector的增删改查操作都是线程同步的。使用了synchronized关键字
> 保证了线程同步.



### LinkedList 的实现原理

> LinkedList 是基于双向链表实现的, 它有一个内部类 Node实现了双向的链表的数据结构,Node 中包含了上一个节点和下一个节点的引用
> LinkedList 有三个重要的成员变量:Node first ,Node last ,int size 
>
> 假如size为10,现在需要在索引为3的位置插入一个元素,则从 first 开始,递归向后查找到第四个元素后,
> 在这个Node前面插入新 Node,  
> 然后维护好这两个Node的指针.如果是在索引为8的位置插入 则从last开始,由后向前递归.
> 查询index为3的元素时,从first开始,由第一个节点递归向后查询到第四个元素返回.



### HashMap 的实现原理

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



### **LinkedHashMap 的实现原理**

>LinkedHashMap 是HashMap的子类，拥有HashMap的所有特性。并自己维护了一个双向链表。类里有两个成员变量 Entry head ;Entry  tail, 分别指向双向链表的表头、表尾。  accessOrder 默认 false ,表示 基于插入顺序 
>
>put 操作: LinkedHashMap并没有重写任何put方法。但是其重写了构建新节点的newNode()方法. 在每次构建新节点时，将新节点链接在内部双向链表的尾部。
>
>get操作：LinkedHashMap的get()方法会改变数据链表，LinkedHashMap具有可预知的迭代顺序：插入顺序、访问顺序。默认是插入顺序排序，如果指定按访问顺序排序，那么调用get方法后会将这次访问的元素放到链表的尾部，不断访问可以形成按访问顺序形成的列表.

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

### **TreeMap 的实现原理**

> TreeMap 初始化的时候会初始化下列参数，第一个Comparator是可以自己定义实现的一个比较的实现，默认为Null,那么默认的比较方式就是compare方法。Entry root;默认为Null。其中Entry内部维护了left,right,parent,color  其中color默认是black。https://www.cnblogs.com/daoluanxiaozi/p/3340382.html
>



### HashSet 的实现原理

>HashSet 聚合了 一个HashMap ，使用HashMap的key来保存数据， 它所有的方法都是调用HashMap对应的方法来实现的



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

## 动态代理及反射

动态代理的几种方式

反射的原理，反射创建类实例的三种方式是什么？

反射中，Class.forName和ClassLoader区别

## 序列化

### UID的作用

反序列化时的UID必须与序列化前的UID相同,否则会序列化失败

### java默认的序列化的缺点

只支持java语言, 性能比较低 

### 其他序列化方案

json, xml, kryo, protocol buf, hesson, thrift, messagepack

![](img/serialized-1.png)

序列化的应用: 深拷贝对象

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



## 泛型

## 网络

### TCP 建立连接的过程

```js
tcp共6个标志位：SYN(synchronous),ACK(acknowledge),PSH(push),FIN(fine),RET(reset),URG(urgent)
client:SYN=1  //在吗
client:SYN_SEND
server:SYN=1,ACK=1  //在的
server:SYN_RCVD
client:ACK=1  //好的
client:ESTABLISED
server:ESTABLISED
交流中...
client:下次聊？
server:好的
client:拜拜
server:再见
```



##IO/NIO

## 多线程

### 线程有哪些状态？

> java中有一个枚举类描述的线程状态，一共6个：new,runable,blocked,timed_waiting,waiting,terminated
>
> 一般认为线程有5个状态，首先是新建状态，调用这个线程的start方法后线程进入就绪状态，CPU调度到这个线程后进入运行状态，遇到IO、网络连接、或者调用sleep、wait 时会进入阻塞状态，需要重新等待CPU的调度，最后是终止状态。

### 同步方法与同步块的区别

> 执行同步方法需要获得当前实例的监视器，执行同步代码块需要获取指定实例的监视器
>
> 同步的实例方法执行时需要获取类对象的监视器，类似于在同步代码块中使用this作为同步对象，这种情况下， 一个实例的某个非静态同步方法不能同时在两个线程中运行，
> 如果这个实例有两个非静态同步方法 a()和 b(),这两个方法也不能同时在两个线程中运行。
> 静态同步方法锁定的是实例的类对象，同一个对象的多个实例,他们的静态同步方法不能在两个线程中同时运行。
> 对于锁定相同对象的方法/代码块不能同时运行在多个线程中。

### 如何让多个线程按顺序执行

通过join方法来实现，主线程中启动了一个子线程，主线程调用子线程的join方法后，子线程完成后主线程才继续执行。也可 以通过线程数为1的线程池来实现

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

共享内存 volatile，消息传递 wait/notfy

### 完成线程同步的方式有哪些

> 1. 使用带有synchronized关键字的同步方法。
> 2. 使用synchronized块。
> 3. 使用JDK 5中提供的java.util.concurrent.lock包中的Lock对象。



什么是死锁

> 两个线程都在等待对方释放锁之后才能继续往下执行，就发生了死锁



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

jdk8的 parallelStream 的理解

分段锁的原理,锁力度减小的思考

## 常用类及接口

### String, StringBuilder,  StringBuffer

> 三者都是使用char[] 的数组保存数据的 ,都是CharSequence 接口的实现类
>
> String中的 concat, replace 等方法不对char[]数组进行操作, 它最终返回一个新的String对象, 所以原String对象一但创建好后它的值将不会改变(没有改变这个值的入口)
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





String，Stringbuffer，StringBuilder的区别？

quartz 和 timer对比

## JVM

### 虚拟机启动参数有哪些类别?

> 1. 准参数（-）, 所有 JVM 都必须实现这些参数的功能，而且向后兼容；
>
>    -D<key>=<value> 设置系统属性,代码中可以通过 System.getProperty("key"); 获取该属性
>
> 2. 扩展参数（-X）, 不保证所有jvm实现都满足，且不保证向后兼容；
>
> 3. 不稳定参数（-XX）, 将来可能会随时取消，需要慎重使用；

### 不稳定参数语法规则

>  -XX:+<option>  表示启用该选项
>
>  -XX:-<option>  表示关闭该选项
>
>  -XX:<option>=<value> 给选项设置一个值

### 怎么查看GC日志?

在JVM启动时 指定参数 `-XX:+PrintGCDetails -Xloggc:./gc.log -XX:+PrintGCDateStamps  `

```
2018-04-13T21:59:38.595+0800: 22.258: GC (Allocation Failure) [PSYoungGen: 214013K->33789K(186368K)] 353197K->253557K(407552K), 0.0560998 secs 
2018-04-13T21:59:38.651+0800: 22.315: Full GC (Ergonomics) [PSYoungGen: 33789K->33765K(186368K)] [ParOldGen: 219768K->219156K(401920K)] 253557K-252921K(588288K), [Metaspace: 2836K->2836K(1056768K)], 2.4230938 secs 
2018-04-13T21:59:44.922+0800: 28.585: GC (Allocation Failure) [PSYoungGen: 186341K->51116K(266752K)] 405497K->301024K(668672K), 0.0650235 secs 
```
### Java 有哪些引用类型

```
强引用, 软引用, 弱引用, 虚引用
```



```
native方法是非Java语言实现的代码
```

常用的GC算法有哪些

JVM内存分代

Java内存模型，以及其在并发中的应用

Java 8的内存分代改进

JVM垃圾回收机制，何时触发MinorGC等操作

答死循环，不断创建对象

jvm中一次完整的GC流程（从ygc到fgc）是怎样的，重点讲讲对象如何晋升到老年代

JVM常用参数

你知道哪几种垃圾收集器，各自的优缺点，重点讲下cms，g1

新生代和老生代的内存回收策略

Eden和Survivor的比例分配等

深入分析了Classloader，双亲委派机制

JVM的编译优化

指令重排序，内存栅栏等

OOM错误，stackoverflow错误，permgen space错误

g1和cms区别,吞吐量优先和响应优先的垃圾收集器选择

说一下强引用、软引用、弱引用、虚引用以及他们之间和gc的关系

# JDBC

### 什么是JDBC

java database connection ,java 数据库连接技术, 是由java提供的一组与平台无关的操作数据库的一组接口标准. 一个类: DriverManager; 四个接口: Connection, Statement, ResultSet, PreparedStatement

### 实现JDBC的技术有哪些

1. JDBC-ODBC桥接技术

   ODBC指的是开放数据库连接, 是由微软提供的数据库连接应用,利用JDBC 操作ODBC 从而实现数据库的连接, 性能较差. 支持的JDBC版本是最新的.

2. JDBC直接连接

   由不同的数据库生产商提供指定的数据库连接驱动程序, 性能是最好的, 一般支持的JDBC版本不是最新的.

3. JDBC网络连接

   使用专门的数据库网络连接命令进行指定主机的数据库操作,此种方式使用最多. 

4. 模拟指定数据库的通讯协议自己编写数据库操作



### JDBC连接数据库的流程

1. 加载数据库的驱动程序, 数据库的生产厂商提供数据库驱动程序来适配 JDBC

2. 进行数据库的连接, 连接地址,用户名,密码

   要连接数据库必须要靠 DriverManager 类完成, 它接收用户名密码, 返回一个Connection 

3. 进行数据库的 操作

4. 关闭数据库

### JDBC体现的设计模式

JDBC操作在取得数据库连接对象时, 采用的是工厂设计模式, DriverManager 是工厂类, Connection 是产品接口, 对于mysql 得到的是 com.mysql.jdbc.JDBC4Connection 实现类 

# web服务器

## tomcat

![](java/nio/BlockingNIO.java)

reader.readLine() 和 inputStream.read(), outputStream.flush() 的坑 

> reader.readLine() 在遇到换行符之前会一直读 , 这就会发生阻塞,  直到遇到换行符的时候才会返回读取结果 ,且 换行符并不返回, 
>
> inputStream.read() 方法执行时如果遇到文件末尾会返回-1,而socket通信时，服务端会一直等待客户端输入. 
>
> 解决办法:  在最后一次读取时由于流里所剩的字节数小于b的长度，流就认为到了流的末尾。如果为整数的话阻塞原因同上。
>
> outputStream.flush()  的作用是清空缓冲区, 并不保证网络传输.  

# spring

Spring AOP与IOC的实现原理

Spring的beanFactory和factoryBean的区别

为什么CGlib方式可以对接口实现代理？

RMI与代理模式

Spring的事务隔离级别，实现原理

对Spring的理解，非单例注入的原理？它的生命周期？循环注入的原理，aop的实现原理，说说aop中的几个术语，它们是怎么相互工作的？

spring的controller是单例还是多例，怎么保证并发的安全



# spring mvc

MVC框架原理，他们都是怎么做url路由的

#spring boot

spring boot特性，优势，适用场景等

#NoSql

# mybatis

Mybatis的底层实现原理

# netty

#设计模式

# 数据库

分库分表技术

# 数据结构

什么是数据结构？

> 数据元素以及元素之间关系的集合

### 怎么去遍历一个二叉树?

```java
import java.util.ArrayList;

public class App {
	private ArrayList<Node> list = new ArrayList<Node>();

	public static void main(String[] args) {
		new App();
	}
	
	public App() {
		Node root = new Node("root");
		Node node2 = new Node("node2");
		Node node3 = new Node("node3");
		Node node4 = new Node("node4");
		Node node5 = new Node("node5");
		Node node6 = new Node("node6");
		Node node7 = new Node("node7");
		root.left = node2;
		node2.right = node3;
		root.right = node4;
		node4.left = node5;
		node4.right = node6;
		node5.right = node7;

		recursive(root);
		for (Node node : list) {
			System.out.println(node.name);
		}
	}

	private void recursive(Node node) {
		if(node!=null) {
			recursive(node.left);
			this.list.add(node);
			recursive(node.right);
		}
	}

	class Node {
		String name;
		Node left;
		Node right;
		public Node(String name) {
			this.name = name;
		}
	}
}
```



树 

二叉树：满二叉树、完全二叉树、最优二叉树(赫夫曼树  )

线性表

线性链表

栈

队列

串

数组

广义表



前序遍历、中序遍历、后续遍历





# 分布式

### 什么是RPC以及RMI

基于网络通信进行接口访问的一种技术实现，它跨平台跨语言，类似于java 的RMI ，

### 如何实现一个RMI程序

>创建远程接口， 并且继承java.rmi.Remote接口
>
>实现远程接口，并且继承：UnicastRemoteObject
>
>创建服务器程序： createRegistry方法注册远程对象，并监听一个端口	
>
>创建客户端程序

### 集群怎么共享session

> mysql/redis： 采用mysql 或 redis 保存session 信息，用户登录的时候会尝试从数据库中获取session ，使用mysql 中性能不是很好，一般使用redis，并且redis也可以做集群
>
> cookie：服务端生产access_token(userid/name/timestamp) 保存在客户端cookie中，接到请求时服务端先看自己有没有这个session，没有的话就使用客户端上送的session，这种方式安全性不高
>
> session同步：服务器之间同步session，由一台服务器生产session，通过脚本或者其他方式同步到其他服务器上，这方案速度慢，同步session有延迟性什么是webservice



### 什么是wsdl

wsdl 他是基于xml定义的一种文件类型，wsdl的作用类似于接口文档，主要对请求和响应的方式和数据格式做说明，一个webservice只有一个wsdl文档 

### 什么是SOAP 简单对象访问协议

## zookeeper

### zookeeper的数据模型

> zookeeper的数据模型和文件系统类似，是一种树形的结构，每个节点称为znode，它是zookeeper中最基本的数据单元，节点可以保存数据和挂载子节点，这些节点的数据保存在内存中，zookeeper会定时把内存中的数据写入到磁盘中。节点分为持久化节点和临时节点，临时节点与客户端会话保持一致，会话失效节点会被清除。

### zookeeper的工作机制

> zookeeper一般暴露使用2181端口对客服端提供服务，客户端向zookeeper注册一个监听（watcher），并维护一个TCP长连接，zookeeper上的节点触发监听事件的时候，zookeeper会向客户端发送一个通知，注册的监听是一次性的，继续监听需要重新注册。
>
> 另外 访问zookeeper 上的资源，需要通过zookeeper的权限验证（ACL）。

### ZooKeeper集群中服务器之间是怎样通信的

Leader服务器会和每一个Follower建立TCP连接，默认2888端口，同时为每个Follower都创建一个叫做LearnerHandler的实体。LearnerHandler主要负责Leader和Follower之间的网络通讯，包括数据同步，请求转发和提议的投票等。Leader服务器保存了所有Follower的LearnerHandler。

### 客户端如何正确处理连接断开的

> 客户端会定时向服务器发送心态报文，zookeeper收到心跳报文后重置超时时间，在session未过期的情况下，客户端会主动在地址列表里选择新的地址进行连接

数据的发布和订阅（配置中心：disconf），负载均衡（dubbo+zookeeper）、命名服务、master选举（kafka、hadoop、hbase）、分布式队列、分布式锁



Dubbo的底层实现原理和机制

```
Dubbo默认采用单一长连接和NIO异步通讯，
适合于小数据量大并发的服务调用，以及消费者数远大于生产者数的情况
1.client一个线程调用远程接口，生成一个ID，Dubbo是使用AtomicLong从0开始累计数字的
2.将接口名称，方法名称，参数值列表，处理结果的回调对象callback，全部封装在一起，组成一个 object
3.向专门存放调用信息的全局ConcurrentHashMap里面put(ID, object)
4.将ID和打包的方法调用信息封装成一对象connRequest，使用IoSession.write(connRequest)异步发送出去
5.当前线程再使用callback的get()方法试图获取远程返回的结果，在get()内部，则使用synchronized获取回调对象callback的锁，  
再先检测是否已经获取到结果，如果没有，然后调用callback的wait()方法，释放callback上的锁，让当前线程处于等待状态。
6.服务端接收到请求并处理后，将结果（此结果中包含了前面的ID，即回传）发送给客户端，客户端socket连接上专门监听消息的线程收到消息，  
分析结果，取到ID，再从前面的ConcurrentHashMap里面get(ID)，从而找到callback，将方法调用结果设置到callback对象里。
7.监听线程接着使用synchronized获取回调对象callback的锁（因为前面调用过wait()，那个线程已释放callback的锁了），再notifyAll()，  
唤醒前面处于等待状态的线程继续执行（callback的get()方法继续执行就能拿到调用结果了），至此，整个过程结束。
```

Dubbo的协议支持

```
dubbo支持的协议有：dubbo/hessian/http/RMI/WebService/thrift/mamcached/redis
```



描述一个服务从发布到被消费的详细过程

分布式系统怎么做服务治理

接口的幂等性的概念

消息中间件如何解决消息丢失问题

Dubbo的服务请求失败怎么处理

重连机制会不会造成错误

对分布式事务的理解

如何实现负载均衡，有哪些算法可以实现？

Zookeeper的用途，选举的原理是什么？

数据的垂直拆分水平拆分。

zookeeper原理和适用场景

zookeeper watch机制

# 高并发

JUC/并发相关



https://www.cnblogs.com/pureEve/p/6546280.html
