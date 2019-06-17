package com.nt.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*SQL> create table Person_tab (pid number(5) primary key,pname varchar2(20),DOB Date, DOJ date,DOM date);

Table created.

SQL> create sequence pid_seq start with 1 increment by 1;

Sequence created.*/

public class DateInsertAppWithMySql {
  private static final String  INSERT_PERSON_DETAILS="INSERT INTO PERSON_TAB(PNAME,DOB,DOJ,DOM) VALUES(?,?,?,?)";
	public static void main(String[] args) {
		 Scanner sc=null;
		 String name=null,sdob=null,sdoj=null,sdom=null;
		 Connection con=null;
		 PreparedStatement ps=null;
		 SimpleDateFormat sdf1=null,sdf2=null;
		 java.util.Date udob=null,udoj=null,udom=null;
		 java.sql.Date sqdob=null,sqdoj=null,sqdom=null;
		 int result=0;
		   try {
			   //read inputs
			   sc=new Scanner(System.in);
			   if(sc!=null) {
				   System.out.println("Enter Person name::");
				   name=sc.nextLine();
				   System.out.println("Enter DOB:: (dd-MM-yyyy)");
				   sdob=sc.next();
				   System.out.println("Enter DOJ:: (MM-dd-yyyy)");
				   sdoj=sc.next();
				   System.out.println("Enter DOM:: (yyyy-MM-dd)");
				   sdom=sc.next();
			   }//if
			   
			   //register JDBC driver s/w
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   //Establish the connection
			   con=DriverManager.getConnection("jdbc:mysql:///NTAJ1111DB","root","root");
			   //create PreparedStatement object
			   if(con!=null) {
				   ps=con.prepareStatement(INSERT_PERSON_DETAILS);
			   }
			   //converting Strng date values to java.sql.Date class objs
			       //for DOB
			      sdf1=new SimpleDateFormat("dd-MM-yyyy");
			      udob=sdf1.parse(sdob);
			      sqdob=new java.sql.Date(udob.getTime());
			      //for DOJ
			      sdf2=new SimpleDateFormat("MM-dd-yyyy");
			      udoj=sdf2.parse(sdoj);
			      sqdoj=new java.sql.Date(udoj.getTime());
			      //for DOM
			      sqdom=java.sql.Date.valueOf(sdom);
			      //set query param values
			      if(ps!=null) {
			    	  ps.setString(1,name);
			    	  ps.setDate(2,sqdob);
			    	  ps.setDate(3,sqdoj);
			    	  ps.setDate(4,sqdom);
			      }
			      //execute the SQL Query
			      if(ps!=null)
			         result=ps.executeUpdate();
			      //process the Result
			      if(result==0)
			    	  System.out.println("Record not inserted found");
			      else
			    	  System.out.println("Record inserted");
		   }
		   catch(SQLException se) {
			   se.printStackTrace();
			   if(se.getErrorCode()==12899)
				   System.out.println("Large values are not allowed");
			   System.out.println("Problem in insertion..");
		   }
		   catch(ClassNotFoundException cnf) {
			   cnf.printStackTrace();
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
		   finally {
			   //close jdbc  objs
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

