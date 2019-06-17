package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace procedure P_AUTH_PRO(user in varchar,
        pass in varchar,
         result out varchar)
as
   cnt number(4);
begin
    select count(*) into cnt from userlist   where uname=user and pwd=pass;
  if(cnt<>0) then
    result:='VALID CREDENTIALS';
  else 
    result:='INVALID CREDENTIALS';
  end if;
end;
/
*/

public class CsAuthTest {
   private static final String  CALL_QUERY="{CALL P_AUTH_PRO(?,?,?) }";
	public static void main(String[] args) {
		 Scanner sc=null;
		 String user=null;
		 String pass=null;
		 Connection con=null;
		 CallableStatement cs=null;
		 try {
			 //read inputs
			 sc=new Scanner(System.in);
			 if(sc!=null) {
				 System.out.println("Enter username:::");
				 user=sc.nextLine();
				 System.out.println("Enter Password:::");
				 pass=sc.nextLine();
			 }
			 // register  JDBC driver s/w
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			 //create CallableStatement object
			 if(con!=null)
				 cs=con.prepareCall(CALL_QUERY);
			 //register OUT params with JDBC types
			 if(cs!=null) 
				 cs.registerOutParameter(3,Types.VARCHAR);
			 //set values to query params
			 if(cs!=null) {
				 cs.setString(1,user);
				 cs.setString(2,pass);
			 }
			 //execute the Query
			 if(cs!=null)
    			 cs.execute();
			 //gather results from Out Params
			 if(cs!=null) {
				 System.out.println("Result::::"+cs.getString(3));
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
					if(cs!=null)
						cs.close();
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
