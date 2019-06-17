package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class FilexibleScrollableRSTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		InputStream is=null;
		Properties props=null;
		String driver=null,url=null,user=null,pwd=null;
		try {
			//Locate the Properties file
			is=new FileInputStream("src/com/nt/commons/jdbc.properties");
			//create Properties object
			props=new Properties();
			//load properties
			props.load(is);
			//get jdbc properties from properties file
			driver=props.getProperty("jdbc.driver");
			url=props.getProperty("jdbc.url");
			user=props.getProperty("jdbc.username");
			pwd=props.getProperty("jdbc.pwd");
		//register JDBC driver s/w
		Class.forName(driver);
		//establish the connection
		con=DriverManager.getConnection(url,user,pwd);
		//create STatement object
		if(con!=null)
				  st=con.createStatement();
		  //send and execute SQL Query
		 if(st!=null)
			 rs=st.executeQuery("SELECT SNO,SNAME,SADD FROM STUDENT");
		 
		 if(rs!=null){
		   System.out.println("Records (TOP-BOTTOM)");
			 while(rs.next()) {
			      System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)); 
		    } //while
		 }
	}//try
    catch(SQLException se) {
    	se.printStackTrace();
    }
	catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		//close jdbc objs
		try {
			if(rs!=null)
				rs.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			if(st!=null)
				st.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			if(con!=null)
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}//finally
}//main
}//class
