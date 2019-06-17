package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessingTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		int result[]=null;
		int sum=0;
         try {
        	 //register JDBC driver s/w
        	 Class.forName("oracle.jdbc.driver.OracleDriver");
        	 //establish the connection
        	 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
        	 //create Statement object
        	 if(con!=null)
        		 st=con.createStatement();
        	 //add Queries to the batch
        	 if(st!=null) {
        		 st.addBatch("INSERT INTO STUDENT VALUES(17,'raja','hyd')");
        		 st.addBatch("UPDATE STUDENT SET SADD='new delhi2' WHERE SNO>=100");
        		 st.addBatch("DELETE FROM STUDENT WHERE SNO>=1000 AND SNO<=8000");
        	 }
        	 //execute the batch
        	 if(st!=null) {
        		 result=st.executeBatch();
        	 }
        	 //process the Result
        	 for(int i=0;i<result.length;++i) {
        		 sum=sum+result[i];
        	 }
        	 
        	 System.out.println("No.of record that effected::"+sum);
        	 
        	 
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
