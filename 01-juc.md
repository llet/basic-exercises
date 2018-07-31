#### 什么是内存可见性问题

##### 代码示例

```java
public class Test01 {
	public static void main(String[] args) {
		ThreadDemo thread = new ThreadDemo();
		thread.start();
		while(true) {
             //即使ThreadDemo线程已经对flg进行了更新.main线程中thread.getFlg() 也有可能返回true;
			if(thread.getFlg()) {
				System.out.println("main done!");
			}
		}
	}
}
class ThreadDemo extends Thread{
	private boolean flg=false;
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		flg=true;
		System.out.println("ThreadDemo done!");
	}
	public boolean getFlg() {
		return flg;
	}
}
```

##### 回答

JVM虚拟机为了优化和提高效率，为每一个线程都提供了一个单独的工作内存。

Java内存模型(JMM)两条规定:

1.线程对共享变量的所有操作都必须在自己的工作内存中进行,不能直接从主内存中读取

2.不同线程之间无法直接访问其他线程工作内存中的变量,线程间变量值的传递需要通过主内存来完成

当一个线程对变量进行修改后,其他线程可能不会及时从主存中读取.