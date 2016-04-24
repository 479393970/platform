package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBDriver;
import model.UserBean;

public class AddBalance extends HttpServlet {
	private DBDriver driver;
	private String feedback;


	public AddBalance() {
		super();
	}


	public void destroy() {
		super.destroy(); 
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username=request.getParameter("username");
		int balancedue= Integer.parseInt(request.getParameter("balancedue"));
		int currentBalance=0;
		try {
			currentBalance=getCurrentBalance(username);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			try {
				driver =new DBDriver();
				HttpSession session = request.getSession();
				this.matchUser(username,balancedue+currentBalance);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
	}
	
	public int getCurrentBalance(String username) throws SQLException{
		UserBean uaer =null;
		String query="SELECT balance from userinfo WHERE username=\""+username+"\" ";
		ResultSet result=driver.executeQuery(query);
		if(!result.next())
			return 0;
		else{
			return result.getInt("balance");
		}
	}
	
	
	
	public void matchUser(String username,int balance) throws SQLException{
		UserBean user = null;
		String query = "Update userinfo SET balance=\""+balance+"\"  WHERE username=\""+username+"\" ";
		ResultSet result = driver.executeQuery(query);	
		if(!result.next())
			feedback="no user matched!";
		else{
			feedback="add balance successfully!";
		}
	}

	

}
