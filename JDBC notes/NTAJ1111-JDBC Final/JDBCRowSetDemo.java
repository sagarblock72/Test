package com.nt.jdbc;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JDBCRowSetDemo {

	public static void main(String[] args) {
		OracleJDBCRowSet jrowset=null;
		try {
			jrowset=new OracleJDBCRowSet();
			jrowset.setUsername("system");
			jrowset.setPassword("manager");
			jrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jrowset.setCommand("SELECT * FROM STUDENT");
			jrowset.execute();
			while(jrowset.next()) {
				System.out.println(jrowset.getInt(1)+"  "+jrowset.getString(2)+"  "+jrowset.getString(3));
			}
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
	}//main
}//class
