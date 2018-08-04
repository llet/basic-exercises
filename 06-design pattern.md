[TOC]

## 设计模式的六大原则

- 开闭原则: 对扩展开放，对修改关闭。
- 里氏代换: 任何父类可以出现的地方，子类一定可以出现。
- 依赖倒转: 针对接口编程，依赖于抽象而不依赖于具体。
- 接口隔离: 使用多个独立的接口，比使用单个接口要好。
- 迪米特法则: 一个实体应当尽量少地与其他实体之间发生相互作用，使得系统功能模块相对独立。
- 合成复用: 尽量使用合成/聚合的方式，而不是使用继承。

## 创建型模式 

### 工厂模式 Factory

简单工厂模式又叫做静态工厂模式,不属于23中设计模式之一,由工厂类根据入参决定创建一个具体的产品.spring的BeanFactory就是简单工厂模式.具体是在传入参数时创建还是传入参数前创建需要根据具体情况来定.

```java
//创建一个接口:
public interface Shape { void draw();}
//创建实现接口的实体类。
public class Rectangle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}
public class Square implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}
public class Circle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}
//创建一个工厂，生成基于给定信息的实体类的对象。
public class ShapeFactory {
   //使用 getShape 方法获取形状类型的对象
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }        
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      return null;
   }
}
//使用该工厂，通过传递类型信息来获取实体类的对象。
public class FactoryPatternDemo {
 
   public static void main(String[] args) {
      ShapeFactory shapeFactory = new ShapeFactory();
      //获取 Circle 的对象，并调用它的 draw 方法
      Shape shape1 = shapeFactory.getShape("CIRCLE");
      //调用 Circle 的 draw 方法
      shape1.draw();
      //获取 Rectangle 的对象，并调用它的 draw 方法
      Shape shape2 = shapeFactory.getShape("RECTANGLE");
      //调用 Rectangle 的 draw 方法
      shape2.draw();
      //获取 Square 的对象，并调用它的 draw 方法
      Shape shape3 = shapeFactory.getShape("SQUARE");
      //调用 Square 的 draw 方法
      shape3.draw();
   }
}
```



### 抽象工厂模式 Abstract Factory
### 单例模式 Singleton
### 建造者模式 Builder
### 原型模式 Prototype

## 结构型模式 

### 适配器模式 Adapter
### 桥接模式 Bridge
### 过滤器模式 Filter、Criteria
### 组合模式 Composite
### 装饰器模式 Decorator
### 外观模式 Facade
### 享元模式 Flyweight
### 代理模式 Proxy

代理模式场景: AOP、 拦截器、 解耦、增强

#### 静态代理

```java
//HelloProxy 、HelloImpl 实现 Hello接口
HelloProxy proxy = new HelloProxy(new HelloImpl());
proxy.hello("world");
```

#### 动态代理 JDK

```java
//HelloImpl 实现 Hello接口
HelloImpl impl = new HelloImpl();
Hello proxy = (Hello)ProxyFactory.getProxy(impl);
proxy.hello("xxx");
```

- 代码实现

```java
public class ProxyFactory {
	public static Object getProxy(Object object) {
		Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), 
         object.getClass().getInterfaces(),
         (proxy1, method, params) -> {
             System.out.println("before...");
             Object result = method.invoke(object, params);
             return result;
         });
		return proxy;
	}
}
```



#### 动态代理 CGLIB

```java
//HelloImpl 可以不实现接口
Object proxy = new HelloInterceptor().getProxy(HelloImpl.class);
HelloImpl helloImpl=(HelloImpl)proxy;
helloImpl.hello("world");
```

- 代码实现

```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.2.5</version>
</dependency>
```

```java
public class HelloInterceptor implements MethodInterceptor {
	private static final Object HelloImpl = null;
	public Object getProxy(Class c) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloImpl.class);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
        throws Throwable {
		System.out.println("日志开始...");
		Object object = proxy.invokeSuper(obj, args);
		System.out.println("日志结束...");
		return object;
	}
}
```



## 行为型模式 

### 责任链模式 Chain of Responsibility
### 命令模式 Command
### 解释器模式 Interpreter
### 迭代器模式 Iterator
### 中介者模式 Mediator
### 备忘录模式 Memento
### 观察者模式 Observer
### 状态模式 State
### 空对象模式 Null Object
### 策略模式 Strategy
### 模板模式 Template
### 访问者模式 Visitor

### AbstractDocument

## J2EE 模式 
### MVC 模式 MVC
### 业务代表模式 Business Delegate
### 组合实体模式 Composite Entity
### 数据访问对象模式 Data Access Object
### 前端控制器模式 Front Controller
### 拦截过滤器模式 Intercepting Filter
### 服务定位器模式 Service Locator
### 传输对象模式 Transfer Object