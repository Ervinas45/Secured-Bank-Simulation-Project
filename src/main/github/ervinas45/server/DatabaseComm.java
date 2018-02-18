package main.github.ervinas45.server;

import net.sf.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseComm {
	
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/secured_bank?user=admin&password=123456789";
    
    public static Boolean login(String uniqueID, String password){
    	Connection conn;
        PreparedStatement preparedStmt;
        ResultSet rs;
		try {
			conn = DriverManager.getConnection(MYSQL_URL);
	        preparedStmt = conn.prepareStatement("SELECT unique_id, password FROM users WHERE unique_id = ? AND password = ?");
	        preparedStmt.setString(1, uniqueID);
	        preparedStmt.setString(2, password);
	        rs = preparedStmt.executeQuery();
	        int count = 0;
	        while (rs.next()) {
	            ++count;
	        }
			if(count == 1){
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static JSONObject checkIfUserExists(String uniqueID){
		
        Connection conn;
        PreparedStatement preparedStmt;
        ResultSet rs;
        JSONObject response;
		try {
			conn = DriverManager.getConnection(MYSQL_URL);
	        preparedStmt = conn.prepareStatement("SELECT unique_id FROM users WHERE unique_id = ?");
	        preparedStmt.setString(1, uniqueID);
	        rs = preparedStmt.executeQuery();
	        int count = 0;

	        while (rs.next()) {
	            ++count;
	        }
	        
			if(count == 1){
				response = new JSONObject();
		        response.put("answer", "true");
		        System.out.println("true");
				return response;
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
//			e.printStackTrace();
		}
		response = new JSONObject();
        response.put("answer", "false");
        System.out.println("false");
		return response;
	}
	
}
