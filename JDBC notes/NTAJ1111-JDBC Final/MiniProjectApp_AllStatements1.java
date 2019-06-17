package com.nt.jdbc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*create or replace procedure P_FIND_PASS_FAIL(m1 in number,
        m2 in number,
         m3 in number,
         result out varchar)as
begin
if(m1<35 or m2<35 or m3<35)then
result:='FAIL';
else
result:='PASS';
end if;
end;
/
SQL> select * from All_student;

       SNO SNAME              M1         M2         M3
      ---------- ---------- ---------- ---------- ----------
       101     raja                90         80         70
       102     rajesh           20         40         50
       103     ravi                40         40         50 
*/
public class MiniProjectApp_AllStatements1 extends JFrame implements ActionListener {
	private static  final String  ALL_SNOS="SELECT SNO FROM ALL_STUDENT";
	private static final String  FETCH_STUD_BY_SNO="SELECT SNAME,M1,M2,M3 FROM ALL_STUDENT WHERE SNO=?";
	private static final String  CALL_QUERY="{CALL P_FIND_PASS_FAIL(?,?,?,?)}";	
	private JLabel  lno,lname,lm1,lm2,lm3,lresult;
	private JComboBox<Integer>  cbno;
	private  JTextField  tname,tm1,tm2,tm3,tresult;
	private JButton bResult,bDetails;
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	private CallableStatement cs;
	private ResultSet rs,rs1;
	
	//constructor
	public  MiniProjectApp_AllStatements1() {
		System.out.println("MiniProjectApp_AllStatments:0-param constructor");
		setTitle("Mini Project App");
		setSize(400, 400);
		setLayout(new FlowLayout());
		//add comps
		lno=new JLabel("student number:");
		add(lno);
		cbno=new JComboBox();
		add(cbno);
		bDetails=new  JButton("Details");
		bDetails.addActionListener(this);
		add(bDetails);
		lname=new JLabel("student name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		lm1=new JLabel("Marks1::");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		lm2=new JLabel("Marks2::");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		lm3=new JLabel("Marks3::");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		bResult=new JButton("Result");
		bResult.addActionListener(this);
		add(bResult);
		lresult=new JLabel("Result:");
		add(lresult);
		tresult=new JTextField(10);
		add(tresult);
		//disable editing on TextBoxes
		tname.setEditable(false);
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tresult.setEditable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JDBC Initialization
		initialize();
		this.addWindowListener(new MyWindowAdapter());
	}
	
	private void initialize() {
		System.out.println("MiniProjectApp_AllStatements.initialize()");
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create STatement object
			st=con.createStatement();
			//send and execute SQL Query
			rs=st.executeQuery(ALL_SNOS);
			//copy records(snos) to ComboBox
			while(rs.next()) {
				cbno.addItem(rs.getInt(1));
			}
			//create PreparedStaetment object
			ps=con.prepareStatement(FETCH_STUD_BY_SNO);
			//create CallableStatement object.
			cs=con.prepareCall(CALL_QUERY);
			cs.registerOutParameter(4,Types.VARCHAR);
			
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
		System.out.println("main(-)");
		new MiniProjectApp_AllStatements1();

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("MiniProjectApp_AllStatements: actionPerformed(-)");
		 int no=0;
		 int m1=0,m2=0,m3=0;
	         if(ae.getSource()==bDetails) {
	        	 System.out.println("Details button is clicked");
	        	 try {
	        		 //read selected item from combox
	        		 no=(int) cbno.getSelectedItem();
	        		 //set values to Query params
	        		 ps.setInt(1,no);
	        		 //execute the query
	        		 rs1=ps.executeQuery();
	        		 //get Record from ResultSet and set to Text boxes
	        		 if(rs1.next()) {
	        			 tname.setText(rs1.getString(1));
	        			 tm1.setText(rs1.getString(2));
	        			 tm2.setText(rs1.getString(3));
	        			 tm3.setText(rs1.getString(4));
	        		 }//if
	        	 }//try
	        	 catch(SQLException se) {
	        		 se.printStackTrace();
	        	 }
	        	 catch(Exception e) {
	        		 e.printStackTrace();
	        	 }
	         }
	         else {
	        	 System.out.println("Result button is clicked");
	        	 try {
	        		 //read marks from TextBoxes
	        		 m1=Integer.parseInt(tm1.getText());
	        		 m2=Integer.parseInt(tm2.getText());
	        		 m3=Integer.parseInt(tm3.getText());
	        		 //set values to IN params
	        		 cs.setInt(1,m1);
	        		 cs.setInt(2,m2);
	        		 cs.setInt(3,m3);
	        		 //execute Query
	        		 cs.execute();
	        		 //gather result from OUT params and set to Text box
	        		 tresult.setText(cs.getString(4));
	        	 }//try
	        	 catch(SQLException se) {
	        		 se.printStackTrace();
	        	 }
	        	 catch(Exception e) {
	        		 e.printStackTrace();
	        	 }
	         }//else
	}//actionPerformed
	
	private class  MyWindowAdapter extends  WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("MiniProjectApp_AllStatements:windowClosing(-)");
			//close jdbc objs
			try {
				   if(rs!=null)
					   rs.close();
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			try {
				   if(rs1!=null)
					   rs1.close();
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			try {
			   if(cs!=null)
				   cs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				   if(ps!=null)
					   ps.close();
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
		}// windowClosing(-)
		
	}//MyWindowAdapter

		

}//class
