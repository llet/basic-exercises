## 1.工厂模式

1. 创建接口 `Shape `
2. 创建两个以上的具体类,分别实现 `Shape` 接口
3. 创建一个`ShapeFactory `工厂类, 编写一个`public Shape getShape(String shapeType){}` 方法, 根据不同的入参返回不同的`Shape`
4. 使用:`new ShapeFactory().getShape("CIRCLE") `

## 2.抽象工厂模式

1. 创建第一个接口`Shape`
2. 创建两个以上的具体类,分别实现 `Shape` 接口
3. 创建第二个接口`Color`
4. 创建两个以上的具体类,分别实现 `Shape` 接口
5. 创建`AbstractFactory.java`,声明两个抽象方法,分别返回`Shape`和`Color`
6. 创建工厂类`ShapeFactory`,继承` AbstractFactory`,根据不同的入参返回不同的Shape
7. 创建工厂类`ColorFactory `,继承` AbstractFactory`,根据不同的入参返回不同的Color
8. 创建工厂生成器`FactoryProducer `,静态方法根据不同的入参返回不同的工厂
9. 使用:`FactoryProducer.getFactory("COLOR").getColor("RED")`

## 3.单例模式

1. 创建一个`SingleObject `类,并在类中定义一个静态私有字段

   `private static SingleObject instance = new SingleObject()`,

   然后定义私有构造函数,

   再定义静态的`getInstance()`方法,返回instance 

2. 使用:`SingleObject.getInstance()`

## 4.建造者模式

1. 创建两个接口 `Food` `Packing`

