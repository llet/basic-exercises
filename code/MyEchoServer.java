import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一个HTTP服务器，响应任意请求，并返回HTTP请求详情
 *
 */
public class MyEchoServer extends Thread{
	public static final int port =80;
	public static void main(String[] args){
		new MyEchoServer().start();
	}
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("监听端口 "+port);
			while(true){
				Socket socket = server.accept();
				new Client(socket).start();
			}
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("启动服务失败");
		}
	}
	public class Client extends Thread{
		private Socket socket;
		private int contentLength =0;
		public Client(Socket socket){
			this.socket=socket;
		}
		@Override
		public void run() {
			try {
				InputStream inputStream = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,Charset.forName("UTF-8")));
				ArrayList<String> headers = new ArrayList<String>();
				//读取Http头
				while(true){
					//对于reader.readline方法，  socket流不会返回null,会一直阻塞 ，所以不使用reader.readline!=null作为循环条件
					String line = reader.readLine();
					headers.add(line);
					System.out.println(line);
					Matcher matcher = Pattern.compile("[cC]ontent-[lL]ength: (.*)").matcher(line);
					if(matcher.matches()){
						String contentLengthStr = matcher.group(1);
						contentLength = Integer.parseInt(contentLengthStr);
					}
					if(line.equals("")){
						break;
					}
				}
				//读取Http body
				String body = "";
				if(contentLength!=0){
					byte[] bytes= new byte[contentLength];
					inputStream.read(bytes);
					body= new String(bytes,Charset.forName("UTF-8"));
					System.out.println(body);
				}
				OutputStream outputStream = socket.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

				
				//先准备好Http body
				StringBuffer resBody = new StringBuffer();
				for(String str:headers){
					resBody.append(str);
					resBody.append("\r\n");
				}
				if(contentLength!=0){
					resBody.append(body);
				}
				//返回http header
				StringBuffer resHeader = new StringBuffer();
				resHeader.append("HTTP/1.1 200 OK\r\n");
				resHeader.append("Content-Type: application/json; charset=UTF-8\r\n");
				resHeader.append("Content-Length: "+resBody.length()+"\r\n");
				resHeader.append("Connection: close\r\n");
				writer.write(resHeader.toString()); 
				writer.newLine();// 根据协议 header与body用空行分割
				//返回Http body
				writer.write(resBody.toString());
				writer.flush();
				
				reader.close();
				inputStream.close();
				writer.close();
				outputStream.close();
			} catch (IOException e) {
				System.out.println("读取客户端消息失败");
			}
			System.out.println("Done.............................");
		}
	}
}
