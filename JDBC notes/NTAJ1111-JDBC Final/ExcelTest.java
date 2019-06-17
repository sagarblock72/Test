package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelTest {
   private static final String  COLLEGE_QUERY="SELECT SNO,SNAME,SADD FROM College.Sheet1";
	public static void main(String[] args) {
		try {
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:excel:///F:/Apps/advjava/JDBC")){
			try(Statement st=con.createStatement()) {
				try(ResultSet rs=st.executeQuery(COLLEGE_QUERY)){
				    while(rs.next()) {
				    	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3));
				    }
				}//try
			}//try
		}//trty
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}//main
}//class
