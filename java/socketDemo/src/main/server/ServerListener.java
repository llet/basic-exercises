package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			while(true) {
				Socket socket = serverSocket.accept();
				//socket传递给新的线程
				System.out.println("接收到新的请求");
				ChatSocket chatSocket = new ChatSocket(socket);
				chatSocket.start();
				chatSocket.out("连接成功\n");
				ChatManager.getChartManager().add(chatSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
