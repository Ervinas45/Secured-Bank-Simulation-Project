package main.github.ervinas45.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

			String line;
			String request;
			JSONObject req;
			
            InputStream is = he.getRequestBody();
            InputStreamReader r = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            
            StringBuilder buf = new StringBuilder();
            while ((line=br.readLine())!=null) {
                buf.append(line);
            }
            request = buf.toString();
            req = JSONObject.fromObject(request);
            
            if(req.containsKey("unique_id") && req.containsKey("password")){
            	System.out.println("1");
            	req = userCheck(req);
            }
            else{
            	System.out.println("2");
            	req = verifyToken(req);
            }
            
            
            
            Headers headers = he.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            he.sendResponseHeaders(200, 0); 
            OutputStream os = he.getResponseBody();
            os.write(req.toString().getBytes("utf-8"));
            os.flush();
            os.close();
            is.close();
        }
	}
	
	public static JSONObject userCheck(JSONObject req){
     
        String uniqueID =  (String) req.get("unique_id");
        String password = (String) req.get("password");
        String token = generateToken(password, uniqueID);
        JSONObject response = new JSONObject();
        response.put("token", token);
		return response;
       
	}
	
	public static String generateToken(String password, String uniqueID){
		String token = null;

		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		        .withIssuer(uniqueID)
		        .withIssuedAt(new Date())
		        .withExpiresAt(new Date(System.currentTimeMillis() + 5000))
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;

	}
	
	public static JSONObject verifyToken(JSONObject req){
		String token = req.getString("token");
		JSONObject response = new JSONObject();
		response.put("answer", "true");
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    JWTVerifier verifier = JWT.require(algorithm)
		    	
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTVerificationException exception){
			response.remove("answer");
			response.put("answer", "false");
		}
		return response;
		
	}
	
//	public static boolean checkCredentials(String uniqueID, String password){
//		if(uniqueID == ID_IN_DATABASE && password == PASSWORD_IN_DATABASE){
//			return true;
//		}
//		return false;
//	}
}
