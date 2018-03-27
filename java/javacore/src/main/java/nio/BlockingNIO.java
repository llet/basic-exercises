package ch01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 要点:通道,缓冲区,选择器
 */
public class BlockingNIO {
	private String PATH = "E:\\workspace1\\nio-demo\\src\\ch01\\demo.png";
	private String PATH1 = "E:\\workspace1\\nio-demo\\src\\ch01\\demo1.png";
	
	@Test
	public void server() {
		try {
			ServerSocketChannel socketChannel = ServerSocketChannel.open();
			socketChannel.bind(new InetSocketAddress(9999));
			
			SocketChannel channel = socketChannel.accept();
			FileChannel fileChannel = FileChannel.open(Paths.get(PATH1), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while((channel.read(buffer))!=-1) {
				buffer.flip();
				fileChannel.write(buffer);
				buffer.clear();
			}
			channel.close();
			fileChannel.close();
		} catch (IOException e) {
		}
		
	}

	@Test
	public void client() {
		SocketChannel channel = null;
		FileChannel fileChannel = null;
		try {
			channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fileChannel = FileChannel.open(Paths.get(PATH), StandardOpenOption.READ);
			while ((fileChannel.read(buffer)) != -1) {
				buffer.flip();
				channel.write(buffer);
				buffer.clear();
			}
			
			System.out.println("客户端传输完成");
		} catch (IOException e) {
			System.err.println("连接服务器失败");
		}
		try {
			channel.close();
			fileChannel.close();
		} catch (Exception e) {
			System.err.println("关闭通道失败");
		}

	}
}
