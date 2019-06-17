package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DateRetrieveTest {
  private static final String  RETRIEVE_PERSON_TAB_QUERIES="SELECT PID,PNAME,DOB,DOJ,DOM FROM PERSON_TAB";
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int pid=0;
		String pname=null;
		java.util.Date udob=null,udoj=null,udom=null;
		java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		String  sdob=null,sdoj=null,sdom=null;
		SimpleDateFormat sdf=null;
		
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement object
			if(con!=null) 
				ps=con.prepareStatement(RETRIEVE_PERSON_TAB_QUERIES);
			//send and execute SQL Query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null) {
				while(rs.next()) {
					//System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getDate(3)+" "+rs.getDate(4)+"  "+rs.getDate(5));
					pid=rs.getInt(1);
					pname=rs.getString(2);
					sqdob=rs.getDate(3);
					sqdoj=rs.getDate(4);
					sqdom=rs.getDate(5);
					//convert java.sql.Date class objs to java.util.Date class objs
					udob=sqdob;
					udoj=sqdoj;
					udom=sqdom;
					//convert  java.util.Date class obj to  String date values
					sdf=new SimpleDateFormat("dd-MM-yyyy");
					sdob=sdf.format(udob);
					sdoj=sdf.format(udoj);
					sdom=sdf.format(udom);
					//display records
					System.out.println(pid+"    "+pname+"   "+sdob+"  "+sdoj+"   "+sdom);
				}
			}//if
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
				if(ps!=null)
					ps.close();
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
