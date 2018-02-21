package main.github.ervinas45.server;

import net.sf.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseComm {
	
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/secured_bank?user=admin&password=123456789";
    
    public static JSONObject getData(String uniqueID){
    	Connection conn;
    	PreparedStatement preparedStmt;
    	ResultSet rs;
    	JSONObject response = new JSONObject();
    	try{
			conn = DriverManager.getConnection(MYSQL_URL);
	        preparedStmt = conn.prepareStatement("SELECT u.name, u.lastname, u.email, u.city, u.bank, a.type, a.funds, a.dept FROM (select name,lastname,email,city,bank from user_info WHERE user_unique_id = ?) u, (select type,funds,dept from user_bank_account WHERE user_unique_id = ?) a");
	        preparedStmt.setString(1, uniqueID);
	        preparedStmt.setString(2, uniqueID);
	        rs = preparedStmt.executeQuery();
	        while (rs.next()) {
	        	response.put("name", rs.getString("name"));
	        	response.put("lastname", rs.getString("lastname"));
	        	response.put("email", rs.getString("email"));
	        	response.put("city", rs.getString("city"));
	        	response.put("bank", rs.getString("bank"));
	        	response.put("type", rs.getString("type"));
	        	response.put("funds", rs.getString("funds"));
	        	response.put("dept", rs.getString("dept"));
	        }
    	}
		catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return (JSONObject) response.put("answer", "Error in database");
		}
    	return response;
    }
    
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
	
	public static JSONObject addFunds(int uniqueid, int funds){
        Connection conn;
        PreparedStatement preparedStmt;
        JSONObject response;
		try {
			conn = DriverManager.getConnection(MYSQL_URL);
	        preparedStmt = conn.prepareStatement("UPDATE `user_bank_account` SET `funds`= funds + ?  WHERE user_unique_id = ?");
	        preparedStmt.setInt(1, funds);
	        preparedStmt.setInt(2, uniqueid);
	        preparedStmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
//			e.printStackTrace();
		}
		response = new JSONObject();
        response.put("answer", "true");
		return response;
	}
	
}
