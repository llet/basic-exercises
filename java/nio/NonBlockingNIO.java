package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

public class NonBlockingNIO {
	private String PATH = "E:\\workspace1\\nio-demo\\src\\main\\java\\nio\\demo.png";
	private String PATH1 = "E:\\workspace1\\nio-demo\\src\\main\\java\\nio\\demo1.png";

	@Test
	public void server() {
		try {
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			channel.bind(new InetSocketAddress(9999));

			Selector selector = Selector.open();
			channel.register(selector, SelectionKey.OP_ACCEPT);

			// 准备就绪事件大于0
			while (selector.select() > 0) {
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while (iterator.hasNext()) {
					SelectionKey sk = iterator.next();
					if (sk.isAcceptable()) {
						SocketChannel channel2 = channel.accept();
						channel2.configureBlocking(false);
						channel2.register(selector, SelectionKey.OP_READ);
					} else if (sk.isReadable()) {
						SocketChannel channel2 = (SocketChannel) sk.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int len = 0;
						while ((len = channel2.read(buffer)) != -1) {
							buffer.flip();
							System.out.print(new String(buffer.array(), 0, len));
							//System.out.println(new String(buffer.array(), 0, len));有bug 不清楚什么原因
							buffer.clear();
						}
					}
					// 取消选择键
					iterator.remove();
				}
			}
		} catch (IOException e) {
			System.out.println("启动服务器失败");
		}

	}

	@Test
	public void client() {
		try {
			SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
			channel.configureBlocking(false);

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			Scanner sc = new Scanner(System.in);

			while (sc.hasNext()) {
				buffer.put(sc.next().getBytes());
				buffer.flip();
				channel.write(buffer);
				buffer.clear();
			}

			channel.close();

		} catch (IOException e) {
			System.out.println("连接服务器失败");
		}
	}
}
