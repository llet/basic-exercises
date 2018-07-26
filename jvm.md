

## JVM

### 虚拟机启动参数

第一种是标准参数: 比如 -version 、 -verbose:class 、-verbose:gc、 -Dfile.encoding=UTF-8、第二种是扩展参数 -Xms512m  -Xmx1024m -Xmn200m -Xss1m -Xloggc:gc.log ,第三种是非标准参数,常用的有: -XX:PermSize -XX:MaxPermSize  -XX:newSize -XX:MaxnewSize -XX:+PrintGCDetails -XX:+PrintGCDateStamps 

### JVM内存模型

线程共享的: 方法区: (常量、静态变量、类的元数据信息), 堆区, 线程私有的: 虚拟机栈, 本地方法栈, 程序计数器

堆区包括: 新生代(Eden+两个Survivor)和 老年代 (OldGen) 

永久代/元空间 (PermGen/Metaspace)

### 怎么查看GC日志?

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