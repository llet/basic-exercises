package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import main.client.view.MainWindow;

public class ClientChatManager {
	private static final ClientChatManager clientChatManager = new ClientChatManager();

	private ClientChatManager() {
	}

	public static ClientChatManager getClientChatManager() {
		return clientChatManager;
	}

	MainWindow mainWindow;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		mainWindow.append("文本框已经和clientChatManager绑定了");
	}

	public void connect(String ip) {
		new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket(ip, 1234);
					writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						mainWindow.append("收到:" + line);
					}
					reader.close();
					writer.close();
					reader = null;
					writer = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void send(String msg) {
		if (writer != null) {
			writer.append(msg+"\n");
			writer.flush();
		} else {
			mainWindow.append("当前连接已经中断");
		}
	}
}
