package com.nt.jdbc;


import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

import oracle.jdbc.rowset.OracleFilteredRowSet;


class Filter1 implements Predicate {
    private String colName;
    private char condData;

    public Filter1(String colName,char condData) {
        this.colName = colName;
        this.condData=condData;
        }

    public boolean evaluate(RowSet rs) {
    	System.out.println("evaluate");
        try {
            CachedRowSet crs = (CachedRowSet) rs;
            String colVal = crs.getString(colName);
            if (colVal!= null && (colVal.charAt(0) == condData)){
            	System.out.println("added...");
            return true;
             } 
            else {
            	System.out.println("not added..");
              return false;
             }
           } catch (Exception e) {
                return false;
          }
       }//emethod

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		return false;
	}

	@Override
	public boolean evaluate(Object value, String columnName)
			throws SQLException {
		return false;
	}
}//Filter1 class

public class FilteredRowsetTest {
   public static void main(String[] args)  {
	   FilteredRowSet  frowset=null;
  	   try{
      //create Filtered Rowset
    	 frowset = new OracleFilteredRowSet();
        frowset.setUsername("system");
        frowset.setPassword("manager");
        frowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        frowset.setCommand("select * from emp");
        //Add filter
        frowset.setFilter(new Filter1("ename",'A'));
          frowset.execute();
           
        while (frowset.next()) {
        	System.out.println(frowset.getInt(1)+"  "+frowset.getString(2)+"  "+frowset.getString(3));
             }
  	   }//try
  	   catch(SQLException se){
  		   se.printStackTrace();
  	   }
  	   catch(Exception e){
  		   e.printStackTrace();
  	   }
   }//main
}//class
