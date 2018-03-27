package ch01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class CopyFileWithNIO {
	private String PATH1 = "E:\\workspace1\\nio-demo\\src\\ch01\\App3.java";
	private String PATH2 = "E:\\workspace1\\nio-demo\\src\\ch01\\App2.txt";
	private String PATH3 = "E:\\workspace1\\nio-demo\\src\\ch01\\App3.txt";

	/**
	 * 了解Buffer的mark,position,remaining,limit,capacity的含义
	 */
	@Test
	public void test1() {
		ByteBuffer buff = testBuffer("allocate", 512, null);
		testBuffer("put", "hello world!", buff);
		testBuffer("flip", null, buff);
		testBuffer("get", 1, buff);
		testBuffer("get", 2, buff);
		testBuffer("mark", null, buff);
		testBuffer("reset", null, buff);
		testBuffer("rewind", null, buff);
		testBuffer("get", 1, buff);
		testBuffer("get", 4, buff);
		testBuffer("position", buff.limit(), buff);
		testBuffer("flip", null, buff);
		testBuffer("clear", null, buff);
		testBuffer("put", ",abcdef", buff);
		testBuffer("flip", null, buff);
		testBuffer("get", 3, buff);
		testBuffer("clear", 4, buff);
	}

	/**
	 * 利用通道和非直接缓冲区复制文件
	 */
	@Test
	public void test2() {
		try {
			// 已知输入流和输出流
			FileInputStream inputStream = new FileInputStream(PATH1);
			FileOutputStream outputStream = new FileOutputStream(PATH2);
			// 获取通道
			FileChannel channel = inputStream.getChannel();
			FileChannel channel2 = outputStream.getChannel();
			// 分配指定大小的缓冲区
			ByteBuffer buff = ByteBuffer.allocate(1024);
			while ((channel.read(buff)) != -1) {
				buff.flip();
				channel2.write(buff);
				buff.clear();
			}
			inputStream.close();
			outputStream.close();
			channel2.close();
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用内存映射文件复制文件
	 */
	@Test
	public void test3() {
		try {
			FileChannel channel = FileChannel.open(Paths.get(PATH1), StandardOpenOption.READ, StandardOpenOption.WRITE);
			FileChannel channel2 = FileChannel.open(Paths.get(PATH3), StandardOpenOption.READ, StandardOpenOption.WRITE,
					StandardOpenOption.CREATE);
			// 内存映射文件 只支持 byteBuffer
			MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, channel.size());
			MappedByteBuffer buffer2 = channel2.map(MapMode.READ_WRITE, 0, channel.size());
			byte[] dst = new byte[buffer.limit()];
			
			buffer.get(dst);
			buffer2.put(dst);

			channel.close();
			channel2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取编码解码器
	 */
	@Test
	public void test4() {
		CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
		CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
	}
	
	
	private void print(ByteBuffer buff) {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		try {
			Field field = ByteBuffer.class.getSuperclass().getDeclaredField("mark");
			field.setAccessible(true);
			map.put("mark", (Integer) field.get(buff));
		} catch (Exception e) {
			System.out.println("打印buffer时获取buffer的mark值失败");
		}
		map.put("position", buff.position());
		map.put("remaining", buff.remaining());
		map.put("limit", buff.limit());
		map.put("capacity", buff.capacity());
		System.out.println(JSON.toJSONString(map));
	}

	private ByteBuffer testBuffer(String operation, Object obj, ByteBuffer buff) {
		System.out.print("执行" + operation + "操作,");
		switch (operation) {
		case "allocate":
			buff = ByteBuffer.allocate((Integer) obj);
			break;
		case "flip":
			buff.flip();
			break;
		case "get":
			Integer i = (Integer) obj;
			byte[] dst = new byte[i];
			buff.get(dst);
			System.out.print(new String(dst)+",");
			break;
		case "clear":
			buff.clear();
			break;
		case "put":
			System.out.print(obj);
			buff.put(((String) obj).getBytes());
			break;
		case "rewind":
			buff.rewind();
			break;
		case "reset":
			buff.reset();
			break;
		case "mark":
			buff.mark();
			break;
		case "position":
			buff.position((Integer)obj);
			break;
		}
		print(buff);
		return buff;
	}
}
