package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseMetaDataTest {

	public static void main(String[] args) {
		Connection con=null;
		DatabaseMetaData dbmd=null;
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create DatabaseMetaData object
			dbmd=con.getMetaData();
			System.out.println("DB MetaData obj class name::"+dbmd.getClass());
			System.out.println("DB name ::"+dbmd.getDatabaseProductName());
			System.out.println("db version::"+dbmd.getDatabaseProductVersion());
			System.out.println("Db major version::"+dbmd.getDatabaseMajorVersion());
			System.out.println("DB minor version::"+dbmd.getDatabaseMinorVersion());
			System.out.println("DB driver name::"+dbmd.getDriverName());
			System.out.println("DB Driver version::"+dbmd.getDriverVersion());
			System.out.println("All Numberic functions::"+dbmd.getNumericFunctions());
			System.out.println("All system functions::"+dbmd.getSystemFunctions());
			System.out.println("All SQL Keywords::"+dbmd.getSQLKeywords());
			System.out.println("Max tables in SELECT SQL Query::"+dbmd.getMaxTablesInSelect());
			System.out.println("Max Connections ::"+dbmd.getMaxConnections());
			System.out.println("supports Procedures ::"+dbmd.supportsStoredProcedures());
			System.out.println("max chars in table name::"+dbmd.getMaxTableNameLength());
			System.out.println("max row size ::"+dbmd.getMaxRowSize());
		}
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
			  if(con!=null)
				  con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
