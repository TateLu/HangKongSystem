package 航空.查询;

import 航空.帮助.*;
import 航空.帮助.SqlBean;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

public class 目的地查询 extends JPanel implements ActionListener
{
	   //用于连接数据库并执行SQL操作的bean
	static SqlBean sqlBean = new SqlBean();
	
	private static DefaultComboBoxModel model_1 = new DefaultComboBoxModel();
	private static DefaultComboBoxModel modelStart = new DefaultComboBoxModel();
	private static DefaultComboBoxModel modelArrive = new DefaultComboBoxModel();
	private static JComboBox jcb1 = new JComboBox(model_1),
	                         jcbStart = new JComboBox(modelStart),
	                         jcbArrive = new JComboBox(modelArrive);
	                  
    private JButton jbQuery1 = new JButton("查询"),
                    jbQuery2 = new JButton("查询"); 
    
       //用于项目选择从每个组合框
    private String destination,start,arrive;
    
       //设置界面的构造方法
    public 目的地查询()
    {    
           //*****************************************************
           
    	JLabel jl = new JLabel("目的地查询");
	    jl.setFont(new Font("Times",Font.BOLD,23));
	    JPanel jpTop = new JPanel();
	    jpTop.add(jl);    
	   
	    JLabel jl2 = new JLabel("查询方法一:");
	    jl2.setFont(new Font("Times",Font.PLAIN,12));
	    
	    JLabel jl3 = new JLabel("        请选择要到达的城市名称:");
	    jl3.setFont(new Font("Times",Font.PLAIN,12));
	    JPanel jpLabel1 = new JPanel(new BorderLayout());
	    jpLabel1.add(jl2,BorderLayout.NORTH);
	    jpLabel1.add(jl3,BorderLayout.CENTER);
	    
	    JPanel jpQuery1 = new JPanel();	   
	    jpQuery1.add(jcb1);
	    jpQuery1.add(jbQuery1);  
	    
	    JPanel jp1 = new JPanel();
	    jp1.setLayout(new BorderLayout());
	    jp1.add(jpLabel1,BorderLayout.NORTH);
	    jp1.add(jpQuery1,BorderLayout.CENTER); 
	    
	       //*****************************************************	    
	    
	    JLabel jl4 = new JLabel("查询方法二:");
	    jl4.setFont(new Font("Times",Font.PLAIN,12));
	    
	    JLabel jl5 = new JLabel("        请选择起始城市和到达城市进行查询:");
	    jl5.setFont(new Font("Times",Font.PLAIN,12));
	    JPanel jpLabel2 = new JPanel(new BorderLayout());
	    jpLabel2.add(jl4,BorderLayout.NORTH);
	    jpLabel2.add(jl5,BorderLayout.CENTER);
	    
	    JPanel jpQuery2 = new JPanel();
	    jpQuery2.add(new JLabel("出发城市:"));
	    jpQuery2.add(jcbStart);
	    
	    JPanel jpQuery3 = new JPanel();
	    jpQuery3.add(new JLabel("抵达城市:"));
	    jpQuery3.add(jcbArrive);
	    
	    JPanel jpButton  = new JPanel();
	    jpButton.add(jbQuery2);
	    
	    JPanel jp2 = new JPanel();
	    jp2.add(jpQuery2);
	    jp2.add(jpQuery3);
	    
	    JPanel jp3 = new JPanel();
	    jp3.setLayout(new BorderLayout());
	    jp3.add(jpLabel2,BorderLayout.NORTH);
	    jp3.add(jp2,BorderLayout.CENTER);
	    jp3.add(jpButton,BorderLayout.SOUTH);
	    
	       //*****************************************************	    
	    
	    JPanel jp4 = new JPanel();
	    jp4.setLayout(new BorderLayout());
	    jp4.add(jp1,BorderLayout.NORTH);
	    jp4.add(jp3,BorderLayout.CENTER);
	    
	    this.setLayout(new BorderLayout());
	    this.add(jpTop,BorderLayout.NORTH);
	    this.add(jp4,BorderLayout.CENTER);
	    this.add(new JLabel("             "),BorderLayout.SOUTH);
	    
	       //将侦听器添加到查询按钮
	    jbQuery1.addActionListener(this);
	    jbQuery2.addActionListener(this);	    
    }
    
    public static void updatePlaceComboBox(String newPlace,int insertOrDelete)
    {
    	if (insertOrDelete == 1)
    	{
    		if (model_1.getIndexOf(newPlace) == -1)
	    	   jcb1.addItem(newPlace);
	    	 
	    	if (modelStart.getIndexOf(newPlace) == -1)
	    	   jcbStart.addItem(newPlace);
	    	   
	    	if (modelArrive.getIndexOf(newPlace) == -1)
	    	   jcbArrive.addItem(newPlace);	
    	}
    	else if (insertOrDelete == 2)
    	{
    		if (model_1.getIndexOf(newPlace) != -1)
    		   jcb1.removeItem(newPlace);
    		
    		if (modelStart.getIndexOf(newPlace) != -1)
    	       jcbStart.removeItem(newPlace);
    		
    		if (modelArrive.getIndexOf(newPlace) != -1)
    		   jcbArrive.removeItem(newPlace);
    	}    	
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource() == jbQuery1)
    	{
    		   //获取要查询的目的地。
    		destination = (String)jcb1.getSelectedItem();
    		destination = destination.trim();
    		
    		   //做查询工作，并给标志= 1，意味着这是第一类查询
    		executeDestinQuery(1);
    	}
    	else if (e.getSource() == jbQuery2)
    	{
    		   //取得出发地
    		start = (String)jcbStart.getSelectedItem();
    		start = start.trim();
    		   //到目的地
    		arrive = (String)jcbArrive.getSelectedItem();
    		arrive = arrive.trim();
    		
    		   //做查询工作，并给标志= 2，意味着这是第二类查询 
    		executeDestinQuery(2);
    	}
    }
    
    public void executeDestinQuery(int flag)
    {
    	   //SQL
    	String sqlString = "SELECT DISTINCT * FROM " + "Plane ";
    	
    	   //两种查询的SQL字符串不同。
    	if (flag == 1)
    	   sqlString += "WHERE destination=" + "\'" + destination + "\'";
    	else 
    	   sqlString += "WHERE start=" + "\'" + start + "\'" + " AND " +
    	                "destination=" + "\'" + arrive + "\'";
    	         
        ResultSet rs = sqlBean.executeSearch(sqlString);
        
        if (rs != null)
	       showResult(rs,flag);
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);  
    }
    

    public void showResult(ResultSet rs,int flag)
    {
        String result = "                                                    " + 
		                "目的地查询 " + "\n";
		
		   //两种查询的结果字符串不同
		if (flag == 1)
		   result += "到达 " + destination + " 的所有航班:" + "\n";
		else
		   result += "始发地: " + start + "------" + "目的地: " + arrive + "\n";
		   
		result += "航班号    航空公司            起飞地点  抵达地点  起飞时间  抵达时间  " + 
		          "儿童票价   成人票价   折扣   班期 " + "\n";
		   
		   //用于确定是否没有记录
		int originLength = result.length();
		
		String time1,time2;
		String childFare,adultFare,discount1,discount2,seat;	
		
		try
		{
			while(rs.next())
			{
				result += rs.getString("flight") + rs.getString("airfirm") + rs.getString("start") + 
				          rs.getString("destination");
				          
				
				time1 = rs.getString("leaveTime");
				time2 = rs.getString("arriveTime");
				 
				time1 = getTime(time1);
				time2 = getTime(time2);
				
				result += time1 + "     " + time2  + "     ";
				
				  
				childFare = String.valueOf(rs.getFloat("childFare"));
				adultFare = String.valueOf(rs.getFloat("adultFare"));
				discount1 = String.valueOf(rs.getFloat("discount1"));
				discount2 = String.valueOf(rs.getFloat("discount2"));
				seat = String.valueOf(rs.getInt("seat"));
				
				  
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
		
		   //意味着找不到记录，所以给用户留言，找不到相关的查询指定类型的信息
		if (result.length() == originLength && flag == 1)
		{
			JOptionPane.showMessageDialog(null,"没有到达 " + destination + "的航班",
	    	                              "查询结果",JOptionPane.PLAIN_MESSAGE);
	    	return;
		}
		if (result.length() == originLength && flag == 2)
		{
			JOptionPane.showMessageDialog(null,"没有从 " + start +" 到 " + arrive +" 的航班",
	    	                              "查询结果",JOptionPane.PLAIN_MESSAGE);
	    	return;
		}
				
	
		JOptionPane.showMessageDialog(null,result,"查询结果",JOptionPane.PLAIN_MESSAGE);		
	}
	

	private String getTime(String time)
	{
		String time1,time2;
		time1 = time.substring(0,2);
		time2 = time.substring(2,4);
		
		time1 = time1.concat(":");
		time1 = time1.concat(time2);
		
		return time1;
	}
    
}///:~