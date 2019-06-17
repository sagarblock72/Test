package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIEmployeeRegistrationApp extends  JFrame  implements ActionListener,WindowListener{
	private static final String INSERT_EMP_QUERY="INSERT INTO EMP(EMPNO,ENAME,JOB,SAL) VALUES(EMPNO_SEQ.NEXTVAL,?,?,?)";
	private JLabel  lname,lsal,ldesg,lresult;
	private  JButton btn;
	private JTextField  tname,tsal,tdesg;
	private Connection con;
	private PreparedStatement ps;
	
	public GUIEmployeeRegistrationApp() {
		System.out.println("GUIEmployeeRegistrationApp:0-param constructor");
		setTitle("Employee Registration App");
		setBackground(Color.CYAN);
		setSize(300, 400);
		setLayout(new FlowLayout());
		//add comps
		lname=new JLabel("Employee name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		lsal=new JLabel("Employee salary::");
		add(lsal);
		tsal=new JTextField(10);
		add(tsal);
		
		ldesg=new JLabel("Employee Desg::");
		add(ldesg);
		tdesg=new JTextField(10);
		add(tdesg);
		
	
		
		btn=new JButton("register");
		btn.addActionListener(this);
		add(btn);
		
		lresult=new JLabel("");
	   add(lresult);
	   setVisible(true);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   //Initialize JDBC
	   initializeJDBC();
	   //add Window Listener
	   this.addWindowListener(this);
		
	}
	
	//helper method
	private void initializeJDBC() {
		System.out.println("GUIEmployeeRegistrationApp::initializeJDBC()");
		try {
			//Load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create JDBC PreparedStatement object
			ps=con.prepareStatement(INSERT_EMP_QUERY);
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
		
	}//makeConnection()
	
	
	
	public static void main(String[] args) {
		System.out.println("GUIEmployeeRegistrationApp.main()");
		new GUIEmployeeRegistrationApp();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("GUIEmployeeRegistrationApp.actionPerformed()");
		String name=null,desg=null;
		int deptno=0,salary=0;
		int count=0;
		try {
			//read text box values
			name=tname.getText();
			desg=tdesg.getText();
			salary=Integer.parseInt(tsal.getText());
			//set values to Query params
			ps.setString(1,name);
			ps.setString(2, desg);
			ps.setInt(3,salary);
			//execute the SQL Query
			count=ps.executeUpdate();
			//process  the Result
			if(count==0) {
				lresult.setForeground(Color.red);
				lresult.setText("Registration Failed");
			}
			else {
				lresult.setForeground(Color.GREEN);
				lresult.setText("Registration Succeded");
			}
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			if(se.getErrorCode()==12899) {
				lresult.setForeground(Color.RED);
				lresult.setText("String values  are too Large");
			}
			else if(se.getErrorCode()==1) {
				lresult.setForeground(Color.RED);
				lresult.setText("Employee Already Registered");
			}
			else if(se.getErrorCode()==1438) {
				lresult.setForeground(Color.RED);
				lresult.setText("Numeric values are too Large");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			lresult.setText("unknown Problem");
		}
	}//actionPerformed(-)

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIEmployeeRegistrationApp.windowClosing()");
		try {
			//close jdbc obj
			if(ps!=null)
				ps.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			//close jdbc obj
			if(con!=null)
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}//class
