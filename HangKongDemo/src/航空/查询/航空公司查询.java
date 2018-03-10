package 航空.查询;

import 航空.帮助.*;
import 航空.帮助.SqlBean;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

public class 航空公司查询 extends JPanel implements ActionListener
{
	   //用于连接数据库并执行SQL操作的bean。
	static SqlBean sqlBean = new SqlBean();	
	   	
	private static DefaultComboBoxModel model = new DefaultComboBoxModel();
	private static JComboBox jcb = new JComboBox(model);
	
	   //用于存储项目在你选择组合框
	private String airfirm;
	
	private JButton jbQuery = new JButton("查询");	
	
	   //设置界面的构造方法
	public 航空公司查询()
	{	
	       //*****************************************************	
	       
		JLabel jl = new JLabel("  航空公司查询");
	    jl.setFont(new Font("Times",Font.BOLD,23));
	    JPanel jpTop = new JPanel();
	    jpTop.add(jl);    
	   
	    JLabel jl2 = new JLabel("        请输入航班名称或者,本查询将");
	    jl2.setHorizontalAlignment(JLabel.CENTER);
	    jl2.setFont(new Font("Times",Font.PLAIN,12));
	    
	    JLabel jl3 = new JLabel("提供你该航空公司所有的航班情况:");
	    jl3.setHorizontalAlignment(JLabel.CENTER);
	    jl3.setFont(new Font("Times",Font.PLAIN,12));
	    JPanel jpLabel = new JPanel(new BorderLayout());
	    jpLabel.add(jl2,BorderLayout.NORTH);
	    jpLabel.add(jl3,BorderLayout.CENTER);
	    
	       //******************************************************
	    
	    JPanel jpBox = new JPanel();
	    jpBox.add(jcb);
	    
	    JPanel jpButton = new JPanel();
	    jpButton.add(jbQuery);
	    
	    JPanel jp = new JPanel();
	    jp.add(jpBox);
	    jp.add(jpButton);
	    
	       //*****************************************************
	    
	    JPanel jpCenter = new JPanel();
	    jpCenter.setLayout(new BorderLayout());
	    jpCenter.add(jpLabel,BorderLayout.NORTH);
	    jpCenter.add(jp,BorderLayout.CENTER);
	    
	    JPanel jpQuery = new JPanel();
	    jpQuery.setLayout(new BorderLayout());
	    jpQuery.add(jpTop,BorderLayout.NORTH);
	    jpQuery.add(jpCenter,BorderLayout.CENTER);	  
	    
	    this.setLayout(new BorderLayout());
	    this.add(new JLabel("        "),BorderLayout.NORTH);
	    this.add(jpQuery,BorderLayout.CENTER);	  
	    
	       //将侦听器添加到查询按钮
	    jbQuery.addActionListener(this);		
	}
	
	public static void updateAirFirmComboBox(String newPlace,int insertOrDelete)
	{
		if (insertOrDelete == 1)
		{
			if (model.getIndexOf(newPlace) == -1)
			   jcb.addItem(newPlace);
		}
		   
		else if (insertOrDelete == 2)
		{
			if (model.getIndexOf(newPlace) != -1)
			   jcb.removeItem(newPlace);
		}
		   
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.airfirm = (String)jcb.getSelectedItem();
		
		   //执行查询工作
		executeAirFirmQuery();
	}
	
	public void executeAirFirmQuery()
	{
		   //SQL字符串
		String sqlString = "SELECT DISTINCT * FROM " +"Plane " +"WHERE airfirm=" + "\'" + airfirm + "\'";              
	    ResultSet rs = sqlBean.executeSearch(sqlString);
	     
	    if (rs != null)
	       showResult(rs);
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);
	}
	
	   //从结果集中获取结果字符串，然后在对话框中显示结果   
	public void showResult(ResultSet rs)
	{
		String result = "                                                     " +
		                "航空公司查询" + "\n";
		
		result += "查询的航空公司:" + airfirm + "\n";
		result += "航班号    航空公司            起飞地点  抵达地点  起飞时间  抵达时间  " + 
		          "儿童票价   成人票价   折扣  班期 " + "\n";
		   
		   //用于确定是否没有记录。     
		int originLength = result.length();
		          
		String time1,time2;
	    String childFare,adultFare,discount1,discount2,seat;	
		
		try
		{
			while(rs.next())
			{
				result += rs.getString("flight") + rs.getString("airfirm") + rs.getString("start") + 
				          rs.getString("destination");
				          
				//当你从结果中得到时间，会得到像1200，我们应该把它改成“12:00”。
				time1 = rs.getString("leaveTime");
				time2 = rs.getString("arriveTime");
				//时间（字符串）是用来改变成标准格式
				time1 = getTime(time1);
				time2 = getTime(time2);
				
				result += time1 + "     " + time2  + "     ";
				
				//确保下列项目有正确的位置，以便它们能以整洁的格式显示			
				childFare = String.valueOf(rs.getFloat("childFare"));
				adultFare = String.valueOf(rs.getFloat("adultFare"));
				discount1 = String.valueOf(rs.getFloat("discount1"));
				discount2 = String.valueOf(rs.getFloat("discount2"));
				seat = String.valueOf(rs.getInt("seat"));
				
				   //使每一个项目格式整齐
				while(childFare.length() != 11)
				   childFare += " ";
				while(adultFare.length() != 11)
				   adultFare += " ";
				while(discount1.length() != 8)
				   discount1 += " ";							
				   
				result += childFare + adultFare + discount1 +
				          rs.getString("week1");
				result += "\n";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		   //意味着没有发现记录，所以，给用户的信息，找不到相关的信息
		if (result.length() == originLength)
		{
			result += "                                                    " +
			          "对不起,找不到你想要的航班信息!" + "\n";
		}
		 	
		   //在消息对话框中显示结果			
		JOptionPane.showMessageDialog(null,result,"查询结果",JOptionPane.PLAIN_MESSAGE);
	}
	
	   //用于改变时间形式的方法。
	private String getTime(String time)
	{
		String time1,time2;
		time1 = time.substring(0,2);
		time2 = time.substring(2,4);
		
		time1 = time1.concat(":");
		time1 = time1.concat(time2);
		
		return time1;
	}
}