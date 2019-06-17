package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessingWithTxMgmtTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		int result[]=null;
		int sum=0;
		boolean flag=false;
         try {
        	 //register JDBC driver s/w
        	 Class.forName("oracle.jdbc.driver.OracleDriver");
        	 //establish the connection
        	 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
        	 //disable autocommit mode
        	 if(con!=null)
        		 con.setAutoCommit(false);
        	 //create Statement object
        	 if(con!=null)
        		 st=con.createStatement();
        	 //add Queries to the batch
        	 if(st!=null) {
        		 st.addBatch("INSERT INTO STUDENT VALUES(12,'raja','hyd')");
        		 st.addBatch("UPDATE STUDENT SET SADD='new delhi6' WHERE SNO>=100");
        		 st.addBatch("DELETE FROM STUDENT WHERE SNO>=15 AND SNO<=50");
        	 }
        	 //execute the batch
        	 if(st!=null) {
        		 result=st.executeBatch();
        	 }
        	 
        	 //Perform Tx mgmt (Transaction Management)
        	 for(int i=0;i<result.length;++i) {
        		  if(result[i]==0) {
        			flag=true;
        			break;
        		  }
        	 }
        	 if(flag==true) {
        		 con.rollback();
        		 System.out.println("Results are rolled back");
        	 }
        	 else {
        		 con.commit();
        		 System.out.println("Results are committed");
        	 }
         }//try
         catch(SQLException se) {
        	 try {
        	 con.rollback();
        	 }
        	 catch(Exception e) {
        		 e.printStackTrace();
        	 }
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
