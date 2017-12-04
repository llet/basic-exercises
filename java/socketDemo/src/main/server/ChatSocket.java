package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatSocket extends Thread {
	Socket socket;

	public ChatSocket(Socket socket) {
		this.socket = socket;
	}

	public void out(String msg) {
		try {
			socket.getOutputStream().write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line ;
			while ((line=reader.readLine())!=null) {
				ChatManager.getChartManager().publish(this, line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
