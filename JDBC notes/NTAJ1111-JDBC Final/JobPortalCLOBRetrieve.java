package com.nt.jdbc;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JobPortalCLOBRetrieve {
  private static final String  RETRIEVE_CLOB_QUERY="SELECT JSID,JSNAME,QLFY,RESUME FROM NAUKRI_JOBPORTAL WHERE JSID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int id=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String name=null,qlfy=null;
		Reader reader=null;
		Writer writer =null;
		char [] buffer=null;
		int charsRead=0;
		
	   try {
		   //read inputs
		   sc=new Scanner(System.in);
		   if(sc!=null) {
			   System.out.println("Enter JobSeeker Id::");
			   id=sc.nextInt();
		   }
		    //register JDBC driver s/w
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    //establish the connection
		    con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		    //create PreparedStatement object
		    if(con!=null)
		    	ps=con.prepareStatement(RETRIEVE_CLOB_QUERY);
		    //set values to query params
		    if(ps!=null)
		    	ps.setInt(1,id);
		    //execute the query
		    if(ps!=null)
		    	rs=ps.executeQuery();
		    //process the ResultSet 
		    if(rs!=null) {
		    	if(rs.next()) {
		    		id=rs.getInt(1);
		    		name=rs.getString(2);
		    		qlfy=rs.getString(3);
		    		reader=rs.getCharacterStream(4);
		    		System.out.println(id+" "+name+"  "+qlfy);
		    		//write STeams based logic to write recived CLOB value to dest file
		    		writer=new FileWriter("new_resume.txt");
		    		buffer=new char[1024];
		    		while( (charsRead=reader.read(buffer))!=-1) {
		    			writer.write(buffer, 0,charsRead);
		    		}//while
		    	}//if
		    	System.out.println("CLOB file is retrieved");
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
			
			try {
				if(reader!=null)
					reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(writer!=null)
					writer.close();
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
