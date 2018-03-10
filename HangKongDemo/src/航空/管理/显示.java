package 航空.管理;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class 显示 extends JPanel implements ActionListener
{
	private JTextField jtf=new JTextField(10);
	private JPasswordField password=new JPasswordField(10);
	private JLabel label1=new JLabel("账号");
	private JLabel label2=new JLabel("密码");
	private JButton button=new JButton("查看");
	private JTextArea area=new JTextArea();
	private JScrollPane pan;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public 显示()
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}
		catch(Exception ex)
		{
		}
		pan=new JScrollPane(area);
		JPanel p1=new JPanel();
		p1.setBorder(new TitledBorder("输入有效认证"));
		p1.add(label1);
		p1.add(jtf);
		p1.add(label2);
		p1.add(password);
		
		JPanel p2=new JPanel();
		p2.setBorder(new TitledBorder("操作"));
		p2.add(button);
		
		this.setLayout(new BorderLayout());
		this.add(p1,BorderLayout.NORTH);
		this.add(pan,BorderLayout.CENTER);
		this.add(p2,BorderLayout.SOUTH);
		
		button.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			
			area.setText("");
			if(!(jtf.getText().equals("0302")&&password.getText().equals("0302")))
			JOptionPane.showMessageDialog(null,"账号或密码不对","错误",JOptionPane.ERROR_MESSAGE);
			else
			{con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=HangKongDB","sa","122026");
	        stmt = con.createStatement();
	        rs=stmt.executeQuery("select * from Plane");
	        while(rs.next())
	        {
	        	area.append(rs.getString(6));
	        	area.append(rs.getString(7));
	        	area.append(rs.getString(8));
	        	area.append(rs.getString(9));
	        	area.append(rs.getString(10));
	        	area.append(rs.getString(11));
	        	area.append(""+rs.getFloat(12)+"  ");
	        	area.append(""+rs.getFloat(13)+"  ");
	        	area.append(""+rs.getFloat(14)+"  ");
	        	area.append(""+rs.getFloat(15)+"  ");
	        	area.append(""+rs.getInt(16)+"  ");
	        	area.append(rs.getString(17));
	        	area.append(rs.getInt(18)+"\n");
	        	
	        }}}
		catch(Exception ex)
		{
		}
		
	}
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex)
		{
			
		}
		JFrame frame=new JFrame();
		frame.getContentPane().add(new 显示());
		frame.setSize(500,450);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}