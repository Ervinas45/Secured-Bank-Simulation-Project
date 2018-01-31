package main.github.ervinas45.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 80), 0);
		System.out.println("server started at 80");
		server.createContext("/test", new MyHandler());
//		server.createContext("/echoHeader", new EchoHeaderHandler());
//		server.createContext("/echoGet", new EchoGetHandler());
//		server.createContext("/echoPost", new EchoPostHandler());
		server.setExecutor(null);
		server.start();
	}
	
	public static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "Response";
            he.sendResponseHeaders(200, response.length());

            InputStream is = he.getRequestBody();
            InputStreamReader r = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line=br.readLine())!=null) {
                buf.append(line);
            }
            String text = buf.toString();
            String[] test = text.split("=");
            for(String t : test){
            	System.out.println(t);
            }

            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
            is.close();
        }
	}
}
