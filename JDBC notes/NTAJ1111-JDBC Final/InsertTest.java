package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTest {

	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		String name=null,addrs=null;
		Connection  con=null;
		Statement st=null;
		String query=null;
		int  count=0;
		try {
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Student number::");
				no=sc.nextInt(); //gives 101
				System.out.println("Enter student name::");
				sc.nextLine();
				name=sc.nextLine(); //gives raja  rao
				System.out.println("Enter Student address");
				addrs=sc.nextLine();  //gives new hyd
			}
			//convert input values as required for the SQL Query
			   name="'"+name+"'"; // gives 'raja rao'
			   addrs="'"+addrs+"'"; //gives  'new hyd'
			   Class.forName("oracle.jdbc.driver.OracleDriver");
				//establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				//create STatement object
				if(con!=null)
					st=con.createStatement();
				//prepare SQL Query
				    //INSERT INTO STUDENT VALUES(901,'chinna','vizag')
				query="INSERT INTO STUDENT VALUES("+no+","+name+","+addrs+")";
				System.out.println(query);
			   //send and execute SQL Query
				if(st!=null)
					count=st.executeUpdate(query);
				//process the Result
				if(count==0)
					System.out.println("Record not inserted");
				else
					System.out.println("Record  inserted");
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()==1)
				System.out.println("Student already inserted with this sno");
			else if(se.getErrorCode()==12899)
				System.out.println("value is  too large for column");
			else if(se.getErrorCode()==933)
				System.out.println("Invalid SQL command");
			else
				System.out.println("Uknown Internal problem");
				
				
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
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}//finally
	}//main
}//class
