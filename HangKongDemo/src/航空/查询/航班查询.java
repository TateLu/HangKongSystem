package 航空.查询;

import 航空.帮助.*;
import 航空.帮助.SqlBean;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;

public class 航班查询 extends JPanel implements ActionListener,ListSelectionListener
{   
       //用于连接数据库并执行SQL操作的bean
	static SqlBean sqlBean = new SqlBean();
	
	private static DefaultListModel model = new DefaultListModel();
    private JList list = new JList(model);	  
	
	private JTextField flightField = new JTextField(15);
	private JButton jbQuery = new JButton("查询");	
	
	   //用来存储你选择的航班号
	private String flightNumber;
	
	   //设置界面的构造方法
	public 航班查询()
	{		 
		   //单选择
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			    
	   
	       //****************************************************
	    JLabel jl = new JLabel("航班查询");
	    jl.setFont(new Font("Times",Font.BOLD,23));
	    JPanel jpTop = new JPanel();
	    jpTop.add(jl);    
	   
	    JLabel jl2 = new JLabel("                   请输入航班名称或者");
	    jl2.setFont(new Font("Times",Font.PLAIN,12));
	    
	    JLabel jl3 = new JLabel("           选择右边的航班列表:");
	    jl3.setFont(new Font("Times",Font.PLAIN,12));
	    JPanel jpLabel = new JPanel(new BorderLayout());
	    jpLabel.add(jl2,BorderLayout.NORTH);
	    jpLabel.add(jl3,BorderLayout.SOUTH);
	    
	       //*******************************************************
	    
	    JPanel jpField = new JPanel();
	    jpField.add(flightField);
	    
	    JPanel jpButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    jpButton.add(jbQuery);
	    
	    JPanel jp1 = new JPanel();
	    jp1.setLayout(new BorderLayout());
	    jp1.add(jpField,BorderLayout.NORTH);
	    jp1.add(jpButton,BorderLayout.CENTER);
	    
	    JPanel jp = new JPanel();
	    jp.setLayout(new BorderLayout());
	    jp.add(jpLabel,BorderLayout.NORTH);
	    jp.add(jp1);	   
	    
	       //*******************************************************
	    
	    JPanel jpList = new JPanel();
	    jpList.add(new JScrollPane(list));
	    
	    JPanel jpCenter = new JPanel();
	    jpCenter.setLayout(new BorderLayout());
	    jpCenter.add(jp,BorderLayout.CENTER);
	    jpCenter.add(jpList,BorderLayout.EAST);
	    
	    JPanel jpQuery = new JPanel();
	    jpQuery.setLayout(new BorderLayout());
	    jpQuery.add(jpTop,BorderLayout.NORTH);
	    jpQuery.add(jpCenter,BorderLayout.CENTER);
	    
	    this.setLayout(new BorderLayout());
	    this.add(new JLabel("      "),BorderLayout.NORTH);
	    this.add(jpQuery,BorderLayout.CENTER);
	    
	       //将侦听器添加到列表中
	    list.addListSelectionListener(this);
	       //将侦听器添加到查询按钮
	    jbQuery.addActionListener(this);	
	}
	
	public static void updateFlightList(String newFlightNum,int insertOrDelete)
	{
		if (insertOrDelete == 1)
		   model.addElement(newFlightNum);
		else if (insertOrDelete == 2)
		{
			if (model.contains(newFlightNum))
			   model.removeElement(newFlightNum);			
		}
		   
	}
	
	   //将操作监控到列表中
	public void valueChanged(ListSelectionEvent e)
	{
		String s = (String)list.getSelectedValue();
		
		if (s != null)
		   flightField.setText(s.trim());
		else 
		   flightField.setText("");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.flightNumber = flightField.getText().trim();	
		
		   //没有输入任何东西
		if (flightNumber.length() == 0)
		{
			JOptionPane.showMessageDialog(null,"请输入航班号或者从列表中选择",
			                              "错误信息",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		   //进行查询工作
		executeFlightQuery();
	}
	
	public void executeFlightQuery()
	{
		   //SQL字符串 
		String sqlString = "select distinct * from " +"Plane " +"WHERE flight=" + "\'" + flightNumber + "\'";
	
	    ResultSet rs = sqlBean.executeSearch(sqlString);		        
	
	    if (rs != null)
	       showResult(rs);
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);
	}
	
	   //从结果集中获取结果字符串,然后在对话框中显示结果。
	public void showResult(ResultSet rs)
	{
		String result = "                                                    " + 
		                "航班查询" + "\n";
		
		result += "查询的航班号:" + flightNumber + "\n";
		result += "航班号    航空公司            起飞地点  抵达地点  起飞时间  抵达时间  " + 
		          "儿童票价   成人票价   折扣1   折扣2   班期 " + "\n";
		   
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
				while(discount2.length() != 8)
				   discount2 += " ";			
				   
				result += childFare + adultFare + discount1 + discount2 +
				          rs.getString("week1");
				result += "\n";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		   //表示没有发现记录，因此显示错误消息。给用户提示输入错误的航班号
		if (result.length() == originLength)
		{
			JOptionPane.showMessageDialog(null,"航班号不存在，请确认输入了正确的航班号",
	    	                              "错误信息",JOptionPane.ERROR_MESSAGE);
	    	return;
		}		
		
		   //在消息对话框中显示结果
		JOptionPane.showMessageDialog(null,result,"查询结果",JOptionPane.PLAIN_MESSAGE);		
	}
	
	   //用于改变时间形式的方法
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