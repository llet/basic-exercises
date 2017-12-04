package main.client;

import java.awt.EventQueue;

import main.client.view.MainWindow;

public class ClientChatSocket {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					ClientChatManager.getClientChatManager().setMainWindow(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
