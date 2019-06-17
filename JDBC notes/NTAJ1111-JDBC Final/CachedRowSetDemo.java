package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetDemo {

	public static void main(String[] args) {
		CachedRowSet crowset=null;
		try {
			crowset=new OracleCachedRowSet();
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setCommand("SELECT * FROM STUDENT");
			crowset.execute();
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3));
			}
			crowset.setReadOnly(false);
			System.out.println("stop DB s/w");
			Thread.sleep(40000);   //stop the DB s/w
			crowset.absolute(2);
			crowset.updateString(3,"vizag");
			crowset.updateRow();
			System.out.println("start DB s/w");
			Thread.sleep(40000);   //start the DB s/w
			crowset.acceptChanges();
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3));
			}
			
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}//main
}//class
