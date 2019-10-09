## 内部类

静态内部类: new ABCPrinterTest.Printer();

内部类: new ABCPrinterTest().new Printer();

## 多线程

### Atomic and volatile

#### 什么是内存屏障

内存屏障 是一个CPU指令。 a) 确保一些特定操作执行的顺序； b) 影响一些数据的可见性。插入一个内存屏障，相当于告诉CPU和编译器先于这个命令的必须先执行，后于这个命令的必须后执行。内存屏障另一个作用是强制更新一次不同CPU的缓存。例如，一个写屏障会把这个屏障前写入的数据刷新到缓存，这样任何试图读取该数据的线程将得到最新值，而不用考虑到底是被哪个cpu核心或者哪颗CPU执行的。

#### volatile 的作用

在多核CPU的计算机中，对非volatile变量，每个线程先将变量从主存拷贝到CPU Cache中，当一个变量定义为 volatile 之后，将具备两种特性，1. 保证可见性和一致性，一个线程修改了变量的值，volatile 保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。2. 保证有序性，禁止编译器、处理器对指令的重排序优化。

#### 什么叫原子性

原子性是指多个线程修改同一个变量时，能保证所有的修改都是有效的，且最终修改的结果是正确的

#### volatile 能保证原子性吗

下面的例子说明 volatile 不能保证原子性

```java
public class TestVolatile {
	private volatile Integer i=0;
	private void increase(){
		i++;
	}
	public static void main(String[] args) throws InterruptedException {
		TestVolatile testVolatile = new TestVolatile();
		ExecutorService executorService = Executors.newCachedThreadPool();
		IntStream.range(0, 2).forEach((x)->{
			executorService.submit(()->{
				IntStream.range(0, 10).forEach((y)->{
					testVolatile.increase();
				});
			});
		});
		executorService.shutdown();
		if(executorService.awaitTermination(10, TimeUnit.SECONDS)){
			System.out.println(testVolatile.i); //14
		}
	}
}
```

#### Atomic 是怎么保证原子性的 

操作系统的原语是指由若干条指令组成的，用于完成一定功能的一个过程。即原语的执行必须是连续的，在执行过程中不允许被中断。CAS 是基于原语的比较和替换算法，当且仅当预期值和内存值相同时，修改内存值，否则什么都不做。 CAS是基于乐观锁的，修改失败会继续尝试。不会有两个线程同时比较并替换一个变量，来保证原子性。

### 锁的一些性质

#### 公平/非公平

公平锁是指多个线程按照申请锁的顺序来获取锁。非公平锁是指多个线程随机获取锁.可以通过 new ReentrantLock(true);获取一个公平锁.

#### 乐观/悲观

悲观锁认为对于同一个数据的并发操作持保守态度,认为会发生修改。因此对于同一个数据的并发操作，必须先获取。syncrhoized是一种悲观锁。

乐观锁则认为对于同一个数据的并发操作，是不会发生修改的。在更新数据的时候，会采用尝试更新，不断重新的方式更新数据。乐观的认为，不加锁的并发操作是没有事情的。

乐观锁在Java中的使用场景，是无锁编程，常常采用的是CAS算法，典型的例子就是原子类，通过CAS自旋实现原子操作的更新

#### 独享/共享

独享锁是指该锁一次只能被一个线程所持有。ReentrantLock Synchronized 是独享锁
共享锁是指该锁可被多个线程所持有。new ReentrantReadWriteLock().readLock();是共享锁

#### 互斥/读写

互斥锁在Java中的具体实现就 ReentrantLock 
读写锁在Java中的具体实现就 ReadWriteLock

#### 偏向/轻量级/重量级

这三种锁是指锁的状态，并且是针对`Synchronized`。在Java 5通过引入锁升级的机制来实现高效`Synchronized`。这三种锁的状态是通过对象监视器在对象头中的字段来表明的。
偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。
轻量级锁是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。

#### 自旋

在Java中，自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

#### 分段

ConcurrentHashMap 就是通过分段锁的形式来实现高效的并发操作。`ConcurrentHashMap`中的分段锁称为Segment，它即类似于HashMap（JDK7与JDK8中HashMap的实现）的结构，即内部拥有一个Entry数组，数组中的每个元素又是一个链表；同时又是一个ReentrantLock（Segment继承了ReentrantLock)。
当需要put元素的时候，并不是对整个hashmap进行加锁，而是先通过hashcode来知道他要放在那一个分段中，然后对这个分段进行加锁，所以当多线程put的时候，只要不是放在一个分段中，就实现了真正的并行的插入。

#### 可重入

同一个线程可以重新获取已经获取到的锁。

```java
synchronized void setA() throws Exception{
    Thread.sleep(1000);
    setB();
}
synchronized void setB() throws Exception{
    Thread.sleep(1000);
}
```



### 练习：生产者消费者模型

生产者生产算术题(如"2 + 5"), 消费者对问题进行计算.

#### 解法1

Producer、Consumer、Question

AtomicInteger、BlockingQueue 、ExecutorService、ScriptEngineManager

```java
public class Producer implements Runnable{
    private volatile boolean isRunning = true;
	private BlockingQueue<Question> queue; 
    private static AtomicInteger count = new AtomicInteger();// 总数
    private static final int SLEEPTIME = 1000;

    public Producer(BlockingQueue<Question> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
    	Question data = null;
        Random r = new Random();
        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(SLEEPTIME));
                data = new Question(count.incrementAndGet());
                System.out.println(data + " 加入队列");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.err.println(" 加入队列失败");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

public class Consumer implements Runnable {
	private BlockingQueue<Question> queue;
	private static final int SLEEPTIME = 1000;
	public Consumer(BlockingQueue<Question> queue) {
		this.queue = queue;
	}
	@Override
	public void run() {
		Random r = new Random();
		try {
			while (true) {
				Question data = queue.take();
				if (data != null) {
					ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
					Object eval = se.eval(data.getQuestion());
					System.out.println(data.toString() + " = " + eval);
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}

public class Question {
	private int index;
	public static Random random=new Random();
	public static String[] operation=new String[]{"+","-","*","/"};
	private String question;
	public Question(int index) {
		this.index=index;
		int x = random.nextInt(9999);
		int i = random.nextInt(4);
		int y = random.nextInt(9999);
		this.question=x+operation[i]+y;
	}
	@Override
	public String toString() {
		return "Q" +index+": "+ question;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
	public static void main(String[] args) throws Exception {
        BlockingQueue<Question> queue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("done");
	}
```



### 练习：多线程打印ABC

使用3个线程依次打印A/B/C,循环10次

#### 解法1  synchronized

```java
public class Test{
    public static void main(String[] args) throws Exception {
        ABCPrinter a = new ABCPrinter("A ");
        ABCPrinter b = new ABCPrinter("B ").setPrev(a);
        ABCPrinter c = new ABCPrinter("C ").setPrev(b);
        a.setPrev(c);

        Thread threadA = new Thread(a);
        Thread threadB = new Thread(b);
        Thread threadC = new Thread(c);
        threadA.start();
        Thread.sleep(10);
        threadB.start();
        threadB.sleep(10);
        threadC.start();

        threadA.join(); // threadA 第10次执行时会停留在 prev.wait() 这一行代码上, ThreadC在第10次执行时会通知ThreadA,从而ThreadA结束

        synchronized (a){
            a.notifyAll(); //ThreadB 需要ThreadA的通知才能结束
        }
        synchronized (b){
            b.notifyAll();//ThreadC 需要ThreadB的通知才能结束
        }
        System.out.println("all done");
    }
}
class ABCPrinter implements Runnable{
    private String name;
    private ABCPrinter prev;
    private int count=0;

    public ABCPrinter setPrev(ABCPrinter prev) {
        this.prev = prev;
        return this;
    }
    public ABCPrinter(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        while (count<10){
            synchronized (prev){ //TheadA 获取ThreadC的监视器 ThreadC的业务代码不能执行
                synchronized (this){ //TheadA 获取自身的监视器 TheadB的业务代码不能执行
                    System.out.print(name); //业务代码
                    count++;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.notifyAll();//TheadA 并通知TheadB
                }
                try {
                    prev.wait(); //TheadA 等待ThreadC执行完成
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

#### 解法2 Lock Condition

```java
public class ABCPrinterTest {
    public static void main(String[] args) throws Exception {
        ThreadGroup group = new ThreadGroup("PrinterGroup");
        Thread printerA = new Thread(group, new Printer("A", 0));
        Thread printerB = new Thread(group, new Printer("B", 1));
        Thread printerC = new Thread(group, new Printer("C", 2));
        // 依次开始A B C线程
        printerA.start();
        Thread.sleep(100);
        printerB.start();
        Thread.sleep(100);
        printerC.start();
        // 等待活动线程结束
        while (group.activeCount() > 0) {
            Thread.yield();
        }
        System.out.println("done");
    }
}

// 打印线程
class Printer implements Runnable {
    private static final int count = 2;
    private static final Lock lock=new ReentrantLock();
    private static final Condition[] conditions = new Condition[]{lock.newCondition(),lock.newCondition(),lock.newCondition()};

    private final Condition thisCondition;
    private final Condition nextCondition;
    private int order;
    private String name;

    public Printer(String name,int order) {
        this.thisCondition=conditions[order];
        this.nextCondition=conditions[(order+1)%3];
        this.name = name;
        this.order = order;
    }
    @Override
    public void run() {
        lock.lock();
        try {
            IntStream.range(0, count).forEach((i)->{
                System.out.print(name);
                nextCondition.signal(); //唤醒下一个线程
                // 最后一次不能等待,否则死锁
                if (i < count - 1) {
                    try {
                        thisCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } finally {
            // 释放打印锁
            lock.unlock();
        }
    }
}
```

## Lambda 



### Overview

```java
//java.util.concurrent.atomic
AtomicInteger atomicInteger = new AtomicInteger(); //0
atomicInteger.incrementAndGet();  //1
atomicInteger.decrementAndGet();  //0
atomicInteger.updateAndGet(x->x+3);  //3
atomicInteger.accumulateAndGet(7, (x,y)->x+3*y);  //24

AtomicIntegerArray array = new AtomicIntegerArray(10);
array.updateAndGet(0, x->x+3);  //[3, 0, 0, 0, 0, 0, 0, 0, 0, 0]

```



### Default Methods for Interfaces

```java
@Test
public void defaultMethodsForInterfaces(){
  Formula f = new Formula() {
    @Override
    public double calculate(int a) {
      return sqrt(a);
    }
  };
  assertEquals(10,f.calculate(100),0);     // 10.0
  assertEquals(10000,Formula.pow(100),0);  //10000
  assertEquals(9, f.sqrt(81),0);
}

interface Formula {
  double calculate(int a);

  default double sqrt(int a) {
    return Math.sqrt(a);
  }
  static double pow(int a) {
    return Math.pow(a, 2);
  }
}
```

### Lambda Expressions

```java 
@Test
public void lambdaExpressions() {
	List<String> list = Arrays.asList("peter", "anna", "mike", "delta");
	list.sort(new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	});

	List<String> list2 = Arrays.asList("peter", "anna", "mike", "delta");
	list2.sort((a, b) -> a.compareTo(b));
	assertEquals(list, list2);
}
```
### Functional Interface 

``` java

@Test
public void functionalInterfaceTest(){
  //函数式接口与单行 lambda 表达式
  Converter<String, Integer> converter = (from)-> Integer.valueOf(from);
  assertEquals(123, converter.convert("123"),0);

  //函数式接口与静态方法
  Converter<String, Integer> converter1 =  Integer::valueOf; 
  assertEquals(1234, converter1.convert("1234"),0);

  //函数式接口与实例方法
  Something something = new Something();
  Converter<String, Integer> converter2 =something::getNumber;
  assertEquals(20, converter2.convert("peter"),0);
  assertEquals(1, converter2.convert(""),0);

  //函数式接口与构造方法
  PersonFactory<Person> factory = Person::new;
  Person person = factory.create("A", "B");
  assertEquals("A", person.firstName);
}
@FunctionalInterface
interface Converter<F,T>{
  T parse(F from);
}
class Something {
  public Integer getNumber(String name){
    return "peter".equals(name)?20:1;
  }
}
class Person {
  String firstName;
  String lastName;
  Person() {}
  Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
interface PersonFactory<P extends Person> {
  P create(String firstName, String lastName);
}
```

### Lambda Scopes

``` java
static int outx=10;
int outy=10;
@Test
public void lambdaScopes(){
	//λ访问局部 final 变量
	final int num = 1;
	Converter<Integer, String> converter = (x) -> String.valueOf(x + num);
	assertEquals("2", converter.convert(1));
	
	//λ访问局部变量 y 
	int y = 1; 
	Converter<Integer, String> converter2 = (x) -> String.valueOf(x + y);
	converter2.convert(2);
    // y是隐式 final 的，不能操作该值,否则编译报错
    //y = 2;
	assertEquals("3", converter2.convert(2));
	
	//λ访问字段
	Converter<Integer, String> converter3 = (x)->String.valueOf(x+outy);
	assertEquals("11",converter3.convert(1));
	outy=0;
	assertEquals("1", converter3.convert(1));
	
	//λ访问静态字段
	Converter<Integer, String> converter4 = (x)->String.valueOf(x+outx);
	assertEquals("11",converter4.convert(1));
	outx=0;
	assertEquals("1", converter4.convert(1));
  
    //无法从lambda表达式中访问接口中的 default 方法
	//Formula formula = (a) -> sqrt(a * 100);	
}
```
### Built-in Functional Interfaces

```java
@Test
public void builtInFunction() {
  // Predicate test
  Predicate<String> predicate = (str) -> str.length() > 0;
  assertEquals(true, predicate.test("test"));
  assertEquals(false, predicate.negate().test("test"));

  Predicate<Object> nonNull = Objects::nonNull;
  Predicate<Object> isNull = Objects::isNull;
  Predicate<String> isEmpty = String::isEmpty;
  Predicate<String> isNotEmpty = isEmpty.negate();

  assertEquals(true, nonNull.test(new Object()));
  assertEquals(true, isNull.test(null));
  assertEquals(true, isEmpty.test(""));
  assertEquals(true, isNotEmpty.test(" "));

  // Function apply
  Function<String, Integer> toInteger = Integer::valueOf;
  Function<Integer, String> toString = String::valueOf;
  assertEquals(123, toInteger.apply("123"), 0);
  assertEquals("123", toString.apply(123));

  // Supplier get
  Supplier<Person> personSupplier = Person::new;
  assertNotNull(personSupplier.get());

  // Consumer accept
  Consumer<Person> greeter = p -> doService(p);
  greeter.accept(new Person("luke", "ooop"));

  // Comparator compare
  Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
  Person p1 = new Person("John", "Doe");
  Person p2 = new Person("Alice", "Wonderland");
  int compare = comparator.compare(p2, p1);// <0
  compare = comparator.reversed().compare(p2, p1); // >0

  // Optional orElse ifPresent
  Optional<Person> optional = Optional.ofNullable(null);
  Person p3 = optional.orElse(new Person("Alice", "Wonderland"));
  optional.ifPresent((p) -> doService(p));

  //Stream 
  ArrayList<String> list = new ArrayList<>();
  list.add("aaa");
  list.add("bbb");
  list.add("ccc");
  list.add("sdf");
  list.add("vvv");
  list.add("acb");
  list.add("cba");
  Stream<String> stream = list.stream();
  stream.filter((str) -> str.contains("c")).forEach(System.out::println);

  list.stream().filter((s) -> s.startsWith("a")).sorted().forEach(System.out::println);
  list.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);

  boolean anyStartsWithA = list.stream().anyMatch((s) -> s.startsWith("a"));
  System.out.println(anyStartsWithA); // true

  boolean allStartsWithA = list.stream().allMatch((s) -> s.startsWith("a"));
  System.out.println(allStartsWithA); // false

  boolean noneStartsWithZ = list.stream().noneMatch((s) -> s.startsWith("z"));
  System.out.println(noneStartsWithZ); // true

  long startsWithB = list.stream().filter((s) -> s.startsWith("b")).count();
  System.out.println(startsWithB); // 3

  Optional<String> reduced = list.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
  reduced.ifPresent(System.out::println);


  int max = 10000;
  List<String> values = new ArrayList<>(max);
  for (int i = 0; i < max; i++) {
    UUID uuid = UUID.randomUUID();
    values.add(uuid.toString());
  }
  long t0 = System.nanoTime();
  long count = values.stream().sorted().count();
  System.out.println(count);
  long t1 = System.nanoTime();
  long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
  System.out.println(String.format("sequential sort took: %d ms", millis));

  //parallel Stream
  long t2 = System.nanoTime();
  long count1 = values.parallelStream().sorted().count();
  System.out.println(count1);
  long t3 = System.nanoTime();
  long millis1 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
  System.out.println(String.format("parallel sort took: %d ms", millis1));


  Map<Integer, String> map = new HashMap<>();
  for (int i = 0; i < 10; i++) {
    map.putIfAbsent(i, "val" + i);
  }
  map.forEach((key, val) -> System.out.println(val));
  map.computeIfPresent(3, (key, val) -> val + key);
  map.get(3);             // val33

  map.computeIfPresent(9, (key, val) -> null);
  map.containsKey(9);     // false

  map.computeIfAbsent(23, key -> "val" + key);
  map.containsKey(23);    // true

  map.computeIfAbsent(3, key -> "bam");
  map.get(3);      

  map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
  map.get(9);             // val9

  map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
  map.get(9);             // val9concat
}

private void doService(Person p) {
  p.lastName = p.lastName + "!";
}
```

### Date API

IntStream/