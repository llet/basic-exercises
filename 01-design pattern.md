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

工厂模式有三个角色： 工厂、产品接口、产品

简单工厂模式又叫做静态工厂模式,不属于23中设计模式之一,由工厂类根据入参决定创建一个具体的产品.spring的BeanFactory就是简单工厂模式.具体是在传入参数时创建还是传入参数前创建需要根据具体情况来定.

```java
ShapeFactory shapeFactory = new ShapeFactory();
Shape shape1 = shapeFactory.getShape("CIRCLE");
Shape shape2 = shapeFactory.getShape("RECTANGLE");
```

### 工厂方法模式

工厂方法模式有四个角色： 工厂接口、工厂、产品接口、产品

让子类工厂去决定实例化哪一个对象。在新增产品时，新增一个对应的工厂即可，不需要修改原有代码。

### 抽象工厂模式 Abstract Factory

抽象工厂模式是工厂方法模式的升级版本，他与工厂方法模式的区别就在于，工厂方法模式针对的是一个产品接口，而抽象工厂模式则是针对的多个产品接口，即抽象工厂可以提供不同接口的产品。

### 单例模式 Singleton

确保一个类只有一个实例。单例模式根据实例化对象时机的不同分为两种：饿汉式，懒汉式。饿汉式单例在单例类被加载时候，就实例化一个对象交给自己的引用；而懒汉式在调用取得实例方法的时候才会实例化对象.

### 建造者模式 Builder
### 原型模式 Prototype

通过调用实例的clone() 方法 创建新的对象。

实现Cloneable 接口，重写一个clone() 方法即完成了原型模式。Object类的clone方法是一个本地方法，它直接操作内存中的二进制流，特别是复制大对象时，比直接new一个对象在性能上要好的多。

#### 深拷贝与浅拷贝

Object类的clone方法只会拷贝对象中的基本的数据类型，对于数组、容器对象、引用对象等都不会拷贝，这就是浅拷贝。如果要实现深拷贝，必须将原型模式中的数组、容器对象、引用对象等另行拷贝。java提供的大部分的容器类都实现了Cloneable接口，所以实现深拷贝并不是特别困难。

## 结构型模式 

### 适配器模式 Adapter

将一个接口转换成另一个接口。

interface A 、class AImpl implements A 、class AImplx implements A 、...
interface B  、class BImpl implements B  、class BAdapter implements B 

```java
interface PlayerB{play(String audioType, String fileName);}
interface PlayerA{
    playVlc(String fileName);
    playMp4(String fileName);
}
class VlcPlayer implements PlayerA{
    @Override playVlc(String fileName) {//代码实现...}
    @Override playMp4(String fileName) {//什么也不做};
}
class Mp4Player implements PlayerA{
    @Override playVlc(String fileName) { /**代码实现...*/}
    @Override playMp4(String fileName) { /**什么也不做*/};
}
class MediaAdapter implements PlayerB{
    PlayerA playerA;
    public MediaAdapter(String audioType){
      if(audioType.equalsIgnoreCase("vlc") ){
         playerA = new VlcPlayer();       
      } else if (audioType.equalsIgnoreCase("mp4")){
         playerA = new Mp4Player();
      }  
   }
    @Override play(String audioType, String fileName) {
        if(audioType.equals("vlc")){
            playerA.playVlc(fileName);
        }else if(audioType.equals("mp4")){
            playerA.playMp4(fileName);
        }
    }
}
class PlayerB implements PlayerB{
    @Override play(String audioType, String fileName) { 
      if(audioType.equals("mp3")){ /**新增播放mp3的功能*/} 
      else if(audioType.equals("vlc") || audioType.equals("mp4")){//适配原有功能
        new MediaAdapter(audioType).play(audioType, fileName); 
      }else{ /**Invalid media. */}
    }
}
```



### 桥接模式 Bridge

我们有一个作为桥接实现的 *DrawAPI* 接口和实现了 *DrawAPI* 接口的实体类 *RedCircle*、*GreenCircle*。*Shape* 是一个抽象类，将使用 *DrawAPI* 的对象。*BridgePatternDemo*，我们的演示类使用 *Shape* 类来画出不同颜色的圆。

```java
public abstract class Shape {
   protected DrawAPI drawAPI; //这里进行桥接
   protected Shape(DrawAPI drawAPI){this.drawAPI = drawAPI;}
   public abstract void draw();  
}
public class Circle extends Shape {
   private int x, y, radius;
   public Circle(int x, int y, int radius, DrawAPI drawAPI) {super(drawAPI);/**其他代码.*/}
   public void draw(){drawAPI.drawCircle(radius,x,y);}
}
//测试
Shape redCircle = new Circle(100,100, 10, new RedCircle());
redCircle.draw();
```



### 过滤器模式 Filter、Criteria

这种模式允许开发人员使用不同的标准来过滤一组对象，通过逻辑运算以解耦的方式把它们连接起来。这种类型的设计模式属于结构型模式，

```java
class Person{
    String name;
    String genger;
    String maritalStatus;
}
public interface Criteria {
   public List<Person> meetCriteria(List<Person> persons);
}
//测试代码
Criteria male = new CriteriaMale();
Criteria female = new CriteriaFemale();
Criteria single = new CriteriaSingle();
Criteria singleMale = new AndCriteria(single, male);
Criteria singleOrFemale = new OrCriteria(single, female);

male.meetCriteria(persons);
female.meetCriteria(persons);
singleMale.meetCriteria(persons);
singleOrFemale.meetCriteria(persons);
```



### 组合模式 Composite

组合模式依据树形结构来组合对象，用来表示部分以及整体层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。

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

两个角色：模板抽象类、实现类

钩子方法：是对于抽象方法或者接口中定义的方法的一个空实现，在实际中的应用，比如说有一个接口，这个接口里有7个方法，而你只想用其中一个方法，那么这时，你可以写一个抽象类实现这个接口，在这个抽象类里将你要用的那个方法设置为abstract,其它方法进行空实现，然后你再继承这个抽象类，就不需要实现其它不用的方法，这就是钩子方法的作用。

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