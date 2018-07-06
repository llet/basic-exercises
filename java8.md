

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

> 将 《volatile 不能保证原子性》的例子中的 private volatile Integer i=0; 替换为 private AtomicInteger i=new AtomicInteger(); 验证Atomic的原子性。

### 练习：生产者消费者模型

Producer、Consumer、BlockingQueue、Question 、ExecutorService

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
        System.out.println("start producting id:" + Thread.currentThread().getId());
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
		this.question=x+""+operation[i]+y;
	}
	@Override
	public String toString() {
		return "Q " +index+": "+ question;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
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
        System.out.println("done");
	}
```



### 练习：多线程打印ABC



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