//web/xml
/**
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>hello</display-name>
  <servlet>
  	<servlet-name>servlet</servlet-name>
  	<servlet-class>MyEchoServlet</servlet-class>
  </servlet>
  <servlet-mapping>
  	<servlet-name>servlet</servlet-name>
  	<url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyEchoServlet extends HttpServlet {

	private static final long serialVersionUID = -5774807377339779327L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String string =request.getMethod()+" "+ request.getRequestURL()+" "+request.getProtocol();
		System.out.println(string);
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String header = headerNames.nextElement();
			System.out.println(header + ": " + request.getHeader(header));
		}
		
		BufferedReader reader = request.getReader();
		String readerStr = "";// 接收用户端传来的JSON字符串(body体里的数据)
		String line;
		while ((line = reader.readLine()) != null) {
			readerStr = readerStr.concat(line);
		}
		System.out.println(readerStr);
		System.out.println();
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Content-Length", "20");
		response.setHeader("Connection", "keep-alive");
		PrintWriter out = response.getWriter();
		out.println("{\"message\":\"sucess\"}");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
