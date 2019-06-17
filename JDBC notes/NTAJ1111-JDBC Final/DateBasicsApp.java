package com.nt.basics;

import java.text.SimpleDateFormat;

public class DateBasicsApp {

	public static void main(String[] args) throws Exception{
		//String s1="45-24-1980"; //dd-MM-yyyy
		String s1="10-12-1980"; //dd-MM-yyyy
		//Converting String date value to java.util.Date class object
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date ud1=sdf.parse(s1);
		System.out.println("Util Date object::"+ud1);
		//Converting  java.util.Date class obj to java.sql.Date class object
		long ms=ud1.getTime();
		java.sql.Date sd1=new java.sql.Date(ms);
		System.out.println("sql Date  object::"+sd1);
		//if String date value pattern is yyyy-MM-dd then it can be converted
		// directly to java.sql.Date class obj with converting it to java.util.Date
		//class object
		String s2="1990-12-25"; //yyyy-MM-dd
		java.sql.Date  sd2=java.sql.Date.valueOf(s2);
		System.out.println("sql date object::"+sd2);
		// Converting java.sql.Date class object  to java.util.Date class object
		//java.util.Date ud2=(java.util.Date)sd2;
		java.util.Date ud2=sd2;
		System.out.println("Util Date obj::"+ud2);
		//Converting java.util.Date class object to String date value
		SimpleDateFormat sdf1=new SimpleDateFormat("dd-yyyy-MMM");
		String s3=sdf1.format(ud2);
		System.out.println(s3);
		
		

	}

}
