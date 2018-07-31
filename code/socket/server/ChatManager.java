package main.server;

import java.util.Vector;

public class ChatManager {
	private static final ChatManager chartManager = new ChatManager();
	private ChatManager() {}
	
	public static ChatManager getChartManager() {
		return chartManager;
	}
	
	Vector<ChatSocket> vector = new Vector<ChatSocket>();
	
	public void add(ChatSocket chatSocket) {
		vector.add(chatSocket);
	}
	
	public void publish(ChatSocket chatSocket,String msg) {
		System.out.println(msg);
		for(ChatSocket chatSocket2:vector) {
			if(!chatSocket2.equals(chatSocket)) {
				chatSocket2.out(chatSocket.getName()+":"+msg+"\n");
			}
		}
	}
}
