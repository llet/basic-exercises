

**问:BootStrapClassLoader 加载哪些jar包**

答:加载 sun.boot.class.path 下的jar包和字节码

```java
String property = System.getProperty("sun.boot.class.path");
for (String string : property.split(";")) 
    System.out.println(string);
//验证
URLClassPath classPath = Launcher.getBootstrapClassPath();
for (URL url : classPath.getURLs())
    System.out.println(url);
```

**问:ExtClassLoader 加载了哪些jar包**

答:加载了 java.ext.dirs 下的所有jar包

```java
String property = System.getProperty("java.ext.dirs");
for (String string : property.split(";")) 
    System.out.println(string);
//验证
URLClassLoader extClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader().getParent();
for(URL url : extClassLoader.getURLs()) 
    System.out.println(url);
```

**问:AppClassLoader 加载了哪些jar包和类**

答:加载  java.class.path 下的所有jar包和字节码

```java
String property = System.getProperty("java.class.path");
for (String string : property.split(";")) 
    System.out.println(string);
//验证
URLClassLoader extClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
for(URL url : extClassLoader.getURLs()) 
    System.out.println(url);
```

**问:ClassLoader的继承关系是什么样子的**

答: APPClassLoader 和 ExtClassLoader 是Launcher的内部类, 它们的父类是 URLClassLoader 父类的父类是 ClassLoader, ClassLoader的父类 是Object. 

**public/protected/(default)/private 使用要注意哪些地方** 

如果不想暴露接口或字段, 用private, 如果想暴露接口给同包名的地方访问 使用默认修饰符, 如果想暴露接口给子类或同包名的地方用protected , 如果想暴露接口给任何地方 用public .  非public的类不能被其它包的类访问. 

RunTime System Launcher ClassLoader

**构造函数有哪些要注意的地方**

如果一个类没有定义构造函数，那么编译器将生成一个默认无参数构造函数,但如果定义了一个构造函数，那么编译器将不再自动生成默认构造函数

**怎么自定义一个类加载器**

不破坏双亲委派模型  首先继承 ClassLoader 类 并覆写 findClass() 方法, 通过IO读取字节码文件,得到字节数组, 然后调用父类的defineClass 方法 , 传入读取到的数组. 就完成了一个自定义类加载器

破坏双亲委派模型 首先继承 ClassLoader 类 覆写loadClass() 方法 和findClass() 方法, 主要调用的几个方法为 findLoadedClass(name) / parent.loadClass(name) / clz = findClass(name); 

**双亲委派模型**

APPClassLoader对 ExtClassLoader : 你帮我找下这个类
ExtClassLoader 对 BootStrapClassLoader: 你帮我找下这个类
BootStrapClassLoader 对 ExtClassLoader : 给你 
ExtClassLoader  对 APPClassLoader :给你

APPClassLoader对 ExtClassLoader : 你帮我找下这个类
ExtClassLoader 对 BootStrapClassLoader: 你帮我找下这个类
BootStrapClassLoader 对 ExtClassLoader : 我这没有, 你自己来 
ExtClassLoader  对 APPClassLoader : 刚找到了, 给你



**class对象的 getDeclaredFields 与 getFields 的区别 **

getFields 获取当前类的 public 字段,  getDeclaredFields  获取所有字段

**问:如何获取类/字段/方法的修饰符?**

clz.getModifiers();

**问: 如何获某个类的父类**

clz.getSupperClass()

**问: 如何获取类实现的接口**

clz.getInterfaces()

**问:访问修饰符有哪些**

public/protected/private 
abstract/static/final/transient/volatile/synchronized/native/strictfp/interface 

**问:如何判断一个class的类型(是枚举/接口/抽象类/注解/类/内部类)**

clz.isInterface() / clz.isEnum() / clz.isAbstract()