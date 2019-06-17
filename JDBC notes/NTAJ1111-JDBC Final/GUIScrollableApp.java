package com.nt.jdbc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIScrollableApp extends JFrame implements ActionListener   {
	private static final String GET_ALL_STUDS="SELECT SNO,SNAME,SADD FROM STUDENT";
	private  JLabel lno,lname,ladd;
	private  JTextField  tno,tname,tadd;
	private JButton bFirst,bNext,bPrevious,bLast;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public  GUIScrollableApp() {
		System.out.println("construtor...");
		setTitle("Scrollable App");
		setSize(300,400);
		setLayout(new FlowLayout());
		//comps
		lno=new JLabel("student number::");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		lname=new JLabel("student name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		ladd=new JLabel("student Address::");
		add(ladd);
		tadd=new JTextField(10);
		add(tadd);
		bFirst=new JButton("First");
		bFirst.addActionListener(this);
		add(bFirst);
		bNext=new JButton("Next");
		bNext.addActionListener(this);
		add(bNext);
		bPrevious=new JButton("Previous");
		bPrevious.addActionListener(this);
		add(bPrevious);
		bLast=new JButton("Last");
		bLast.addActionListener(this);
		add(bLast);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initialize();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("GUIScrollableApp::windowClosing(-)");
			     try {
			    	 if(rs!=null)
			    		 rs.close();
			     }
			     catch(SQLException se) {
			    	 se.printStackTrace();
			     }
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
			}//windowClosing(-)
		}//anonymous inner class
		);
	}//constructor
	
	private void  initialize() {
		System.out.println("initialize()");
		try {
			//registe JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create Statement object
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//create ResultSet object
			rs=st.executeQuery(GET_ALL_STUDS);
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
		
	}

	public static void main(String[] args) {
		System.out.println("main(-) method");
		  new GUIScrollableApp();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		boolean flag=false;
		try {
		System.out.println("GUIScrollableApp.actionPerformed()");
		if(ae.getSource()==bFirst) {
			rs.first();
			flag=true;
		}
		else if(ae.getSource()==bNext) {
			if(!rs.isLast()) {
				rs.next();
				flag=true;
			}
		}
		else if(ae.getSource()==bPrevious) {
			if(!rs.isFirst()) {
				rs.previous();
				flag=true;
			}
		}
		else {
			rs.last();
			flag=true;
		}
		
       if(flag==true) {
    	   //set value to Text Boxes
    	   tno.setText(rs.getString(1));
    	   tname.setText(rs.getString(2));
    	   tadd.setText(rs.getString(3));
       }
		
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
}//actionPerformed(-)
