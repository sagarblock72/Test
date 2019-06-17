package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*SQL> create sequence Bank_ACNO_SEQ start with 1000 increment by 1;

Sequence created.

SQL> desc  BankAccount;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 ACNO                                      NOT NULL NUMBER(10)
 HOLDERNAME                                         VARCHAR2(10)
 ADDRS                                              VARCHAR2(10)
 BALANCE                                            NUMBER(10,2)
*/
public class AccountOpeningAppWithAutoAcno {
	private static final String  INSERT_QUERY="INSERT INTO BANKACCOUNT VALUES(BANK_ACNO_SEQ.NEXTVAL,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String name=null,addrs=null;
		float balance=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Applicant's Name::");
				name=sc.nextLine();
				System.out.println("Enter address");
				addrs=sc.nextLine();
				System.out.println("Initial Amount::");
				balance=sc.nextFloat();
			}
			//register  DRIVER 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish  the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			if(con!=null)
				ps=con.prepareStatement(INSERT_QUERY);
			//set values to Query params
			if(ps!=null) {
				ps.setString(1,name);
				ps.setString(2,addrs);
				ps.setFloat(3,balance);
			}
			//execute the query
			if(ps!=null)
				result=ps.executeUpdate();
			//gather results
			if(result==0)
				System.out.println("Problem in Opening Bank Account");
			else
				System.out.println("Account opened Successfully");
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
				if(sc!=null)
					sc.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			
		}//finally
	}//main
}//class
