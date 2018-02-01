package main.github.ervinas45.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.sf.json.JSONObject;

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

        	String token;
			String line;
			
            InputStream is = he.getRequestBody();
            InputStreamReader r = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            
            StringBuilder buf = new StringBuilder();
            while ((line=br.readLine())!=null) {
                buf.append(line);
            }
            
            JSONObject jsonObject = JSONObject.fromObject(buf.toString());
            
            String uniqueID =  (String) jsonObject.get("unique_id");
            String password = (String) jsonObject.get("password");
            
            //for(String t : splittedRequest){
            //	System.out.println(t);
            //}
            
//          if(checkCredentials(int uniqueID, String password) == true){
//        	  token = generateToken();
//            Headers headers = he.getResponseHeaders();
//            headers.add("test", token);
//            he.sendResponseHeaders(200, 0);
//            OutputStream os = he.getResponseBody();
//            String response = "";
//            os.write(response.getBytes());
//            os.close();
//            is.close();
//        }
//        else{
//        	  Headers headers = he.getResponseHeaders();
//            headers.add("ERROR: ", "Bad ID or Password");
//            he.sendResponseHeaders(400, 0);
//            OutputStream os = he.getResponseBody();
//            String response = "ERROR: Bad ID or Password";
//            os.write(response.getBytes());
//            os.close();
//            is.close();
//        }
            token = generateToken(password);
            Headers headers = he.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            he.sendResponseHeaders(200, 0); 
            
            JSONObject jsonRespond = new JSONObject();
            jsonRespond.put("token", token);
            
            OutputStream os = he.getResponseBody();
            os.write(jsonRespond.toString().getBytes("utf-8"));
            os.flush();
            os.close();
            is.close();
        }
	}
	
	public static String generateToken(String password){
		
		String token = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256("password");
		    token = JWT.create()
		        .withIssuer("auth0")
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;

	}
	
//	public static boolean checkCredentials(String uniqueID, String password){
//		if(uniqueID == ID_IN_DATABASE && password == PASSWORD_IN_DATABASE){
//			return true;
//		}
//		return false;
//	}
}
