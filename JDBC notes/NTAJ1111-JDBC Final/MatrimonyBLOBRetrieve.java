package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MatrimonyBLOBRetrieve {
  private static final String MATRIMONY_RETRIEVE_QUERY="SELECT ID,NAME,AGE,GENDER,PHOTO FROM JODIMAKERS_MATRIMONY WHERE ID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int id=0;
		String name=null,gender=null;
		int age=0;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=null;
		int bytesRead=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("EnterApplicant Id::");
				no=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(MATRIMONY_RETRIEVE_QUERY);
			//set values Query params
			if(ps!=null)
				ps.setInt(1,no);
			//send and execute SQL Query
			if(ps!=null) {
				rs=ps.executeQuery();
			}
			//process the ResultSEt object
			if(rs!=null) {
				if(rs.next()) {
					id=rs.getInt(1);
					name=rs.getString(2);
					age=rs.getInt(3);
					gender=rs.getString(4);
					is=rs.getBinaryStream(5);
					System.out.println(id+"   "+name+"  "+age+"  "+gender);
					//write BLOB value Dest file
					os=new FileOutputStream("pict.jpg");
					buffer=new byte[4096];
					while((bytesRead=is.read(buffer))!=-1) {
						os.write(buffer, 0, bytesRead);
					}
				}//if
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(os!=null)
					os.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
			try {
				if(is!=null)
					is.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
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
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
