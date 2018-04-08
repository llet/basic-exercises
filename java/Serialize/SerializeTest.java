package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.junit.Test;

public class SerializeTest {

	/**
	 * 对象转换为字节数组
	 */
	@Test
	public void ObjectToByte() throws Exception {
		Person p = new Person("tomcat", "召唤师峡谷", 99, 888888);

		byte[] bytes;
		ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
		ObjectOutputStream stream2 = new ObjectOutputStream(stream1);
		stream2.writeObject(p);
		bytes = stream1.toByteArray();

		for (byte b : bytes) {
			System.out.print(b + ",");
		}
		/**
		 * -84,-19,0,5,115,114,0,18,99,111,109,46,101,120,97,109,112,108,101,46,80,101,114,115,
		 * 111,110,12,-29,92,27,17,-42,47,-52,2,0,3,73,0,6,110,117,109,98,101,114,76,0,7,97,100,
		 * 100,114,101,115,115,116,0,18,76,106,97,118,97,47,108,97,110,103,47,83,116,114,105,
		 * 110,103,59,76,0,4,110,97,109,101,113,0,126,0,1,120,112,0,13,-112,56,116,0,15,-27,
		 * -113,-84,-27,-108,-92,-27,-72,-120,-27,-77,-95,-24,-80,-73,116,0,6,116,111,109,99,
		 * 97,116,
		 */

		System.out.println("done!");
	}

	/**
	 * 字节数组转对象
	 */
	@Test
	public void ByteToObject() throws Exception {

		byte[] bytes = new byte[] { -84, -19, 0, 5, 115, 114, 0, 18, 99, 111, 109, 46, 101, 120, 97, 109, 112, 108, 101,
				46, 80, 101, 114, 115, 111, 110, 12, -29, 92, 27, 17, -42, 47, -52, 2, 0, 3, 73, 0, 6, 110, 117, 109,
				98, 101, 114, 76, 0, 7, 97, 100, 100, 114, 101, 115, 115, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97,
				110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 0, 4, 110, 97, 109, 101, 113, 0, 126, 0, 1, 120, 112,
				0, 13, -112, 56, 116, 0, 15, -27, -113, -84, -27, -108, -92, -27, -72, -120, -27, -77, -95, -24, -80,
				-73, 116, 0, 6, 116, 111, 109, 99, 97, 116 };
		ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		Person p = (Person) stream.readObject();

		p.mailCheck();
		System.out.println(p);
	}

	/**
	 * 对象转换成文件
	 */
	@Test
	public void ObjectToFileStream() throws Exception {
		Person p = new Person("tomcat", "召唤师峡谷", 99, 888888);
		FileOutputStream stream1 = new FileOutputStream("./person");
		ObjectOutputStream stream2 = new ObjectOutputStream(stream1);
		stream2.writeObject(p);

		stream1.close();
		stream2.close();

		System.out.println("done!");
	}

	/**
	 * 文件转对象
	 */
	@Test
	public void FileStreamToObject() throws Exception {
		FileInputStream stream = new FileInputStream("./person");
		ObjectInputStream stream2 = new ObjectInputStream(stream);
		Person p = (Person) stream2.readObject();

		p.mailCheck();
		System.out.println(p);
	}

	/**
	 * ServerSocket 将对象序列化后 通过TCP传输给客户端
	 */
	@Test
	public void sendObjectToClient() throws Exception {
		ServerSocket serverSocket = new ServerSocket(888);

		Socket socket = serverSocket.accept();

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
			ObjectOutputStream stream3 = new ObjectOutputStream(stream2);
			Person p = new Person(line, "召唤师峡谷", 99, 888888);
			stream3.writeObject(p);
			OutputStream stream = socket.getOutputStream();
			stream.write(stream2.toByteArray());
			stream.flush();
		}

	}

	/**
	 * Socket 客户端拿到字节流后反序列化得到java对象
	 */
	@Test
	public void getObjectFromServer() throws Exception {
		Socket socket = new Socket("localhost", 888);
		OutputStream stream = socket.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			writer.write(line);
			writer.newLine();
			writer.flush();
			ObjectInputStream stream2 = new ObjectInputStream(socket.getInputStream());
			Person p = (Person) stream2.readObject();
			System.out.println("接收到服务端返回:" + p);
		}
	}

}

class Person implements Serializable {
	private String name;
	private String address;
	private transient int SSN;
	private int number;

	public Person(String name, String address, int sSN, int number) {
		this.name = name;
		this.address = address;
		SSN = sSN;
		this.number = number;
	}

	public void mailCheck() {
		System.out.println("Mailing a check to " + name + " " + address);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSSN() {
		return SSN;
	}

	public void setSSN(int sSN) {
		SSN = sSN;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", address=" + address + ", SSN=" + SSN + ", number=" + number + "]";
	}

}
