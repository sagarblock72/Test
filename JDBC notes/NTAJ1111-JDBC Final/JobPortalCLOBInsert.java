package com.nt.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JobPortalCLOBInsert {
   private static final String  INSERT_CLOB_QUERY="INSERT INTO NAUKRI_JOBPORTAL VALUES(JSID_SEQ.NEXTVAL,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String name=null;
		String qlfy=null;
		String resumePath=null;
		File file=null;
		Reader reader=null;
		long lenght=0;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter JobSeeker name::");
				name=sc.next();
				System.out.println("Enter JobSeeker Qualification::");
				qlfy=sc.next();
				System.out.println("Enter Resume file Path::");
				sc.nextLine();
				resumePath=sc.nextLine();
			}//if
			//Locate the file
			  file=new File(resumePath);
			  //get the length of the file
			  lenght=file.length();
			 //create ReaderStream
			  reader=new FileReader(file);
			  //register JDBC driver s/w
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			  //establish the connection
			  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			  //create PreparedStatement obj
			  if(con!=null)
				  ps=con.prepareStatement( INSERT_CLOB_QUERY);
			  //set values to query params
			  if(ps!=null) {
				  ps.setString(1,name);
				  ps.setString(2, qlfy);
				  ps.setCharacterStream(3, reader,lenght);
			  }
			  //execute the Query
			  if(ps!=null) {
				  result=ps.executeUpdate();
			  }
			  //gather the results
			  if(result==0)
				    System.out.println("Record not inserted");
			  else
				  System.out.println("record inserted");
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
				if(reader!=null)
					reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
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
