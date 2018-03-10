
package 航空.查询;

import 航空.帮助.*;
import 航空.帮助.SqlBean;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class 综合查询 extends JPanel implements ActionListener,ItemListener
{
	   //用于连接数据库并执行SQL操作的bean
	static SqlBean sqlBean = new SqlBean();
	
	   //*********************************************************************
	   //模型组合框的位置
    private static DefaultComboBoxModel modelPlace = new DefaultComboBoxModel();   
       //对于航空公司的组合模型
    private static DefaultComboBoxModel modelAirFirm = new DefaultComboBoxModel();
       //年的组合项目
    private static Object[] year = {"2017","2018"};
       //月组合框
 	private static Object[] month = {"1","2","3","4","5","6","7","8","9","10","11","12"};
 	   //天的组合框
 	private static Object[] day = {"1","2","3","4","5","6","7","8","9","10","11","12",
 	                               "13","14","15","16","17","18","19","20","21","22",
 	                               "23","24","25","26","27","28","29","30","31"}; 	                        
 	                         	
 	private  static JComboBox jcbStart = new JComboBox(),jcbFirstArrive = new JComboBox(),
 	                          jcbArrive = new JComboBox(),jcbAirFirm = new JComboBox(modelAirFirm),
 	                          jcbYear1 = new JComboBox(year),jcbYear2 = new JComboBox(year),
 	                          jcbMonth2 = new JComboBox(month),jcbMonth1 = new JComboBox(month),
 	                          jcbDay1 = new JComboBox(day),jcbDay2 = new JComboBox(day);
 	   //*********************************************************************
 	                   
 	private JRadioButton jrbSingle = new JRadioButton("单程",true),
 	                     jrbDouble = new JRadioButton("往返",false),
 	                     jrbMutiple = new JRadioButton("联程",false);
 	   
 	   //程序应该动态地改变标签中的内容，所以我们应该把jlabels为成员变量，这样我们就可以在这个类的每一个方法中改变它们的内容
 	private JLabel jlStart,jlFirstArrive,jlArrive,
 	               jlTime1,jlTime2,jlAirFirm,
 	               jlReplaceArrive,jlReplaceTime;
 	                   
       //程序应该动态地删除并添加组件到框架中。根据你选择的查询模式，所以我们应该把JPanels作为成员变量，
 	   //这样我们就可以在这个类的每个方法中删除和添加面板中的组件
    private JPanel jpFirstArriveBox,jpTime2Box,jpReplaceArrive,jpArrive1,jpReplaceTime,jpTime2; 
       
    private JButton jbQuery = new JButton("查询");
    
       //用于项目选择从每个组合框
    private String start,firstArrive,arrive,leaveWeek,leaveWeek2,backWeek,airFirm,
                   leaveYear,leaveMonth,leaveDay,backYear,backMonth,backDay,
                   leaveYear2,leaveMonth2,leaveDay2;
      
       //设置界面的构造方法
    public 综合查询()
    {   
        jcbAirFirm.addItem("所有");
        jcbAirFirm.setSelectedItem("所有");
    
           //让时间组合框显示当前时间的程序
    	setDisplayPresentTime();
    	
    	   //*********************************************************************
    	
    	JPanel jp1 = new JPanel();
    	jp1.add(jrbSingle);
    	JPanel jp2 = new JPanel();
    	jp2.add(jrbDouble);
    	JPanel jp3 = new JPanel();
    	jp3.add(jrbMutiple);    	
    	
    	JPanel jpRadioButton = new JPanel();
    	jpRadioButton.setLayout(new GridLayout(5,1)); 
    	jpRadioButton.add(new JLabel("       "));   	
    	jpRadioButton.add(jp1);
    	jpRadioButton.add(jp2);
    	jpRadioButton.add(jp3);
    	jpRadioButton.add(new JLabel("       "));
    	
    	ButtonGroup bg = new ButtonGroup();
    	bg.add(jrbSingle);
    	bg.add(jrbDouble);
    	bg.add(jrbMutiple);
    	
    	   //*********************************************************************
    	
    	JPanel jpStart = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpStart.add(jlStart = new JLabel("        出发城市:"));
    	
    	JPanel jpFirstArrive = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpFirstArrive.add(jlFirstArrive = new JLabel("                "));
    	
    	JPanel jpArrive = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpArrive.add(jlArrive = new JLabel("        到达城市:"));
    	
    	JPanel jpTime1Tip = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpTime1Tip.add(jlTime1 = new JLabel("        出发日期:"));
    	
    	JPanel jpTime2Tip = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpTime2Tip.add(jlTime2 = new JLabel("              "));
    	
    	JPanel jpAirFirm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	jpAirFirm.add(jlAirFirm = new JLabel("       航空公司:"));
    	
    	JPanel jpLabels = new JPanel();
    	jpLabels.setLayout(new GridLayout(7,1));    	
    	jpLabels.add(jpStart);
    	jpLabels.add(jpFirstArrive);
    	jpLabels.add(jpArrive);
    	jpLabels.add(jpTime1Tip);
    	jpLabels.add(jpTime2Tip);
    	jpLabels.add(jpAirFirm);
    	jpLabels.add(new JLabel("            "));
    	
    	   //*********************************************************************
    	            
        JPanel jpStartBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpStartBox.add(jcbStart);
        
           //***********************
           //组合框”jcbfirstarrive”应动态删除和添加到jpfirstarrivebox，
           //因此，创建一个面板包含一个空的标签，用于替换面板包含组合框”jcbfirstarrive”，当我们不需要它时
        jpReplaceArrive = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpReplaceArrive.add(jlReplaceArrive = new JLabel("            "));
    	
    	jpArrive1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpArrive1.add(jcbFirstArrive);
    	
        jpFirstArriveBox = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    	jpFirstArriveBox.add(jpReplaceArrive);
    	   //***********************    	   
    	  
    	JPanel jpArriveBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpArriveBox.add(jcbArrive);
    	
    	JPanel jpTime1Box = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpTime1Box.add(jcbYear1);
    	jpTime1Box.add(jcbMonth1);
    	jpTime1Box.add(jcbDay1);
    	
    	   //***********************
    	jpReplaceTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpReplaceTime.add(jlReplaceTime = new JLabel("            ")); 
    	
    	jpTime2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpTime2.add(jcbYear2);
    	jpTime2.add(jcbMonth2);
    	jpTime2.add(jcbDay2);
    	
    	jpTime2Box = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    	jpTime2Box.add(jpReplaceTime); 
    	  	//***********************    	  	 	
    	    
    	JPanel jpAirFirmBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpAirFirmBox.add(jcbAirFirm);
    	
    	JPanel jpButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	jpButton.add(jbQuery);
    	
    	JPanel jpComboBox = new JPanel();
    	jpComboBox.setLayout(new GridLayout(7,1));    
    	jpComboBox.add(jpStartBox);
    	jpComboBox.add(jpFirstArriveBox);
    	jpComboBox.add(jpArriveBox);
    	jpComboBox.add(jpTime1Box);
    	jpComboBox.add(jpTime2Box);
    	jpComboBox.add(jpAirFirmBox);
    	jpComboBox.add(jpButton);
    	
    	    //*********************************************************************
        
        JPanel jpQuery = new JPanel();
        jpQuery.setLayout(new BorderLayout());
        jpQuery.add(jpLabels,BorderLayout.WEST);
        jpQuery.add(jpComboBox,BorderLayout.CENTER);
        
        JPanel jpDown = new JPanel();
        jpDown.setLayout(new BorderLayout());
        jpDown.add(jpRadioButton,BorderLayout.WEST);
        jpDown.add(jpQuery,BorderLayout.CENTER);
        
        JLabel jlTitle = new JLabel("综合查询");
        jlTitle.setHorizontalAlignment(JLabel.CENTER);
        jlTitle.setFont(new Font("Tims",Font.BOLD,23));
        this.setLayout(new BorderLayout());
        this.add(jlTitle,BorderLayout.NORTH);
        this.add(jpDown,BorderLayout.CENTER);;
        
           //添加侦听器到单选按钮 
           //radiolistener是一个内部类，定义如下
        jrbSingle.addActionListener(new RadioListener());
        jrbDouble.addActionListener(new RadioListener());
        jrbMutiple.addActionListener(new RadioListener());
        
           //添加监听器的时间组合框
        jcbYear1.addItemListener(this);
        jcbYear2.addItemListener(this);
        jcbMonth1.addItemListener(this);
        jcbMonth2.addItemListener(this);
        
           //将侦听器添加到查询按钮
        jbQuery.addActionListener(this);    	
    }
    
    public static void updatePlaceComboBox(String newPlace,int insertOrDelete)
    {
    	if (insertOrDelete == 1)
    	{
    		if (modelPlace.getIndexOf(newPlace) == -1)
    		{
	    		modelPlace.addElement(newPlace);
	    		jcbStart.addItem(newPlace);	
	    		jcbFirstArrive.addItem(newPlace);	
	    		jcbArrive.addItem(newPlace);	
    		}
	    	
    	}
    	else if (insertOrDelete == 2)
    	{
    		if (modelPlace.getIndexOf(newPlace) != -1)
    		{
	    		modelPlace.removeElement(newPlace);
	    		jcbStart.removeItem(newPlace);
	    		jcbFirstArrive.removeItem(newPlace);
	    		jcbArrive.removeItem(newPlace);	
    		}    		
    	}   	
    }
    
    public static void updateAirFirmComboBox(String newAirFirm,int insertOrDelete)
    {
    	if (insertOrDelete == 1)
    	{
    		if (modelAirFirm.getIndexOf(newAirFirm) == -1)
    		   jcbAirFirm.addItem(newAirFirm);
    	}
    	   
    	else if (insertOrDelete == 2)
    	{
    		if (modelAirFirm.getIndexOf(newAirFirm) != -1)
	    	   jcbAirFirm.removeItem(newAirFirm);
    	}
    	   
    }
    
       //让时间框显示当前时间的方法，当你第一次操作时
    public void setDisplayPresentTime()
    {
    	   //获取用于获取当前时间
    	Calendar cal = Calendar.getInstance();
    	
    	   //因为有两个日期类(java.util.Date--java.sql.Date)
    	   //所以我们应该为转化为指定格式类指定姓名
    	cal.setTime(new java.util.Date());
    	
    	   //得到现在，年，月，日
    	String year = String.valueOf(cal.get(Calendar.YEAR));
    	String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    	String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    	
    	   //让时间组合框显示当前时间
    	jcbYear1.setSelectedItem(year);
    	jcbYear2.setSelectedItem(year);
    	jcbMonth1.setSelectedItem(month);
    	jcbMonth2.setSelectedItem(month);
    	
    	   //我们应该改变项目当天ComboBox的动态，根据年份和月份
    	updateDay(year,month,jcbDay1);
    	updateDay(year,month,jcbDay2);    	
    	
    	jcbDay1.setSelectedItem(day);
    	jcbDay2.setSelectedItem(day);    	
    }
    
       //改变天数
    private void updateDay(String year,String month,JComboBox jcb)
    {
    	   //有30天的月4,6,9,11
    	if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11"))    	   
    	{
    		   //jcb.getitemcount（）＝＝31意味着有31天，但事实上只有30天，所以要在Combox中移除31天
    		if (jcb.getItemCount() == 31)
    		   jcb.removeItem("31");	
    		else if(jcb.getItemCount() == 29)    		
    		   jcb.addItem("30");    	
    		else if (jcb.getItemCount() == 28)
    		{
    			jcb.addItem("29");
    			jcb.addItem("30");
    		}    	
    	}
    	   //2月有28天或29天
    	else if (month.equals("2"))
    	{
    		int years = Integer.parseInt(year);
    		
    		   //今年是闰年
    		if ( (years % 400 == 0) || (years %4 == 0 && years % 100 != 0))
    		{
    			if (jcb.getItemCount() == 31)
    			{
    				jcb.removeItem("30");
    			    jcb.removeItem("31");    		    			
    			}
    			else if (jcb.getItemCount() == 30)
    			{
    				jcb.removeItem("30");
    			}
    			else if (jcb.getItemCount() == 28)
    			{
    				jcb.addItem("29");
    			}
    		}
    		   //今年不是闰年
    		else 
    		{
    			if (jcb.getItemCount() == 29)
    			{
    				jcb.removeItem("29");
    			}
    			else if (jcb.getItemCount() == 30)
    			{
    				jcb.removeItem("29");
    				jcb.removeItem("30");
    			}
    			else if (jcb.getItemCount() == 31)
    			{
    				jcb.removeItem("29");
    				jcb.removeItem("30");
    				jcb.removeItem("31");
    			}
    		}
    	}
    	   //在剩下的月份里有31天   	
    	else 
    	{
    		if (jcb.getItemCount() == 28)
    		{
    		    jcb.addItem("29");
    		    jcb.addItem("30");
    		    jcb.addItem("31");	
    		}
    		else if (jcb.getItemCount() == 29)
    		{
    			jcb.addItem("30");
    			jcb.addItem("31");    			
    		}
    		else if (jcb.getItemCount() == 30)
    		{
    			jcb.addItem("31");
    		}    		
    	}
    }
    
       //时间的组合框的监测方法
    public void itemStateChanged(ItemEvent e)
    {
    	   //根据你选择的年月改变天组合
    	if (e.getSource() == jcbYear1 || e.getSource() == jcbMonth1)
    	{
    		String year = (String)jcbYear1.getSelectedItem();
    		String month = (String)jcbMonth1.getSelectedItem();    		
    		   
    		updateDay(year,month,jcbDay1);
    	}
    	   //The same reason as the above one
    	if (e.getSource() == jcbYear2 || e.getSource() == jcbMonth2)
    	{
    		String year = (String)jcbYear2.getSelectedItem();
    		String month = (String)jcbMonth2.getSelectedItem();
    		
    		updateDay(year,month,jcbDay2);
    	}
    }
    
       //对于JRadioButton的侦听器的内部类
    class RadioListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		   //根据您选择的查询模式 动态地删除并添加组件到框架中
               
               //如果你选择单查询模式  		  
    		if (jrbSingle.isSelected())
    		{
    			jlFirstArrive.setText("            ");
    			   //除去在jpfirstarrivebox面板目前的组件
    			jpFirstArriveBox.removeAll(); 
    			   //将指定组件添加到指定查询模式中的面板中
    			jpFirstArriveBox.add(jpReplaceArrive);
    			   //使用方法repaint()这样组件就添加到面板并可以立即显示
    			jpFirstArriveBox.repaint();
    			
    			jlArrive.setText("    到达城市:");
    			jlTime1.setText("    出发日期:");
    			jlTime2.setText("            ");
    			
    			jpTime2Box.removeAll();
    			jpTime2Box.add(jpReplaceTime); 
    			jpTime2Box.repaint();  			
    		}
    		   //如果选择返程
    		else if(jrbDouble.isSelected())
    		{
    			jlFirstArrive.setText("            ");
    			jpFirstArriveBox.removeAll();
    			jpFirstArriveBox.add(jpReplaceArrive);
    			jpFirstArriveBox.repaint();
    			
    			jlArrive.setText("    到达城市:");    			
    			jlTime1.setText("    出发日期:");
    			jlTime2.setText("    返程日期:");
    			
    			jpTime2Box.removeAll();
    			jpTime2Box.add(jpTime2);
    			jpTime2Box.repaint();
    		}
    		   //如果选择多路查询模式
    		else if (jrbMutiple.isSelected())
    		{
    			jlFirstArrive.setText("第一到达城市:");
    			jpFirstArriveBox.removeAll();
    			jpFirstArriveBox.add(jpArrive1);
    			jpFirstArriveBox.repaint();
    			
    			jlArrive.setText("第二到达城市:");
    			jlTime1.setText("第一出发日期:");
    			jlTime2.setText("第二出发日期:");
    			
    			jpTime2Box.removeAll();
    			jpTime2Box.add(jpTime2);
    			jpTime2Box.repaint();
    		}    		
    	}
    }
    
       //“jbquery”按钮监测方法
    public void actionPerformed(ActionEvent e)
    {
    	   //根据你选择的查询模式，操作是不同的。
    	   
    	   //如果你选择单查询模式
        if (jrbSingle.isSelected())
        {
        	   //取得出发地
        	start = (String)jcbStart.getSelectedItem();	
        	start = start.trim();
        	
        	   //获得目的地
        	arrive = (String)jcbArrive.getSelectedItem();
        	arrive = arrive.trim();
        	
        	   //得到出发时间
        	leaveYear = (String)jcbYear1.getSelectedItem();
        	leaveMonth = (String)jcbMonth1.getSelectedItem();
        	leaveDay = (String)jcbDay1.getSelectedItem();
        	
        	   //判断你选择的时间是否有效
        	if (!isTimeValid(leaveYear,leaveMonth,leaveDay))
        	{
        		   //如果时间无效，向用户显示错误消息
        		JOptionPane.showMessageDialog(null,"已经过了出发时间,请重新设定并查询",
        		                              "错误信息",JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	   //方法timetoweek是用来将指定周一到周日，就像把"2004,12,25" 转成周六(Saturday)
        	leaveWeek = timeToWeek(leaveYear,leaveMonth,leaveDay);
        	
        	   //获得你想要的航班公司
        	airFirm = (String)jcbAirFirm.getSelectedItem();
        	airFirm = airFirm.trim();
        	
        	   //执行查询工作
        	executeSingleQuery();
        }
           //如果选择返程模式
        else if(jrbDouble.isSelected())
        {
        	start = (String)jcbStart.getSelectedItem();
        	start = start.trim();        	   
        	
        	arrive = (String)jcbArrive.getSelectedItem();
        	arrive = arrive.trim();
        	
        	   //获得出发时间
        	leaveYear = (String)jcbYear1.getSelectedItem();
        	leaveMonth = (String)jcbMonth1.getSelectedItem();
        	leaveDay = (String)jcbDay1.getSelectedItem();
        	   //获得到达时间
        	backYear = (String)jcbYear2.getSelectedItem();
        	backMonth = (String)jcbMonth2.getSelectedItem();
            backDay = (String)jcbDay2.getSelectedItem();
        	
        	   //判断你选择的时间是否有效
        	if (!isTimeValid(leaveYear,leaveMonth,leaveDay))
        	{
        		JOptionPane.showMessageDialog(null,"已经过了出发时间,请重新设定并查询",
        		                              "错误信息",JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	if (!isTimeValid(leaveYear,leaveMonth,leaveDay,backYear,backMonth,backDay))
        	{
        		JOptionPane.showMessageDialog(null,"返程日期不能比出发日期早,请重新设定并查询",
        		                              "错误信息",JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	leaveWeek = timeToWeek(leaveYear,leaveMonth,leaveDay);
        	backWeek = timeToWeek(backYear,backMonth,backDay);
        	
        	airFirm = (String)jcbAirFirm.getSelectedItem();
        	airFirm = airFirm.trim();
        	
        	executeDoubleQuery();
        }
           //如果选择多路查询模式
        else if (jrbMutiple.isSelected())
        {
        	start = (String)jcbStart.getSelectedItem();
        	start = start.trim();
        	   //获得中途目的地
        	firstArrive = (String)jcbFirstArrive.getSelectedItem();
        	firstArrive = firstArrive.trim();
        	   //获得目标目的地
        	arrive = (String)jcbArrive.getSelectedItem();
        	arrive = arrive.trim();
        	   
        	   //获得出发城市的出发时间
        	leaveYear = (String)jcbYear1.getSelectedItem();
        	leaveMonth = (String)jcbMonth1.getSelectedItem();
        	leaveDay = (String)jcbDay1.getSelectedItem();
        	   //获得中间城市的出发时间
        	leaveYear2 = (String)jcbYear2.getSelectedItem();
        	leaveMonth2 = (String)jcbMonth2.getSelectedItem();
        	leaveDay2 = (String)jcbDay2.getSelectedItem();
        	
        	   //判断你选择的时间是否有效
        	if (!isTimeValid(leaveYear,leaveMonth,leaveDay))
        	{
        		JOptionPane.showMessageDialog(null,"已经过了出发时间,请重新设定并查询",
        		                              "错误信息",JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	if (!isTimeValid(leaveYear,leaveMonth,leaveDay,leaveYear2,leaveMonth2,leaveDay2))
        	{
        		JOptionPane.showMessageDialog(null,"返程日期不能比出发日期早,请重新设定并查询",
        		                              "错误信息",JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	leaveWeek = timeToWeek(leaveYear,leaveMonth,leaveDay);
        	leaveWeek2 = timeToWeek(leaveYear2,leaveMonth2,leaveDay2);
        	
        	airFirm = (String)jcbAirFirm.getSelectedItem();
        	airFirm = airFirm.trim();
        	
        	executeMutipleQuery();
        }        	
    }  
    
       //用来判断你选择的时间是否有效的方法。如果你选择的时间比现在早，那就不是有效的。
    private boolean isTimeValid(String year,String month,String day)
    {
    	int y = Integer.parseInt(year);
    	int m = Integer.parseInt(month);
    	int d = Integer.parseInt(day);
    	
    	   //得到现在的时间
    	Calendar cal = Calendar.getInstance();
    	 
    	cal.setTime(new java.util.Date());
    	
    	int py = cal.get(Calendar.YEAR);
    	int pm = cal.get(Calendar.MONTH) + 1;
    	int pd = cal.get(Calendar.DAY_OF_MONTH);
    	
    	if (y == py)
    	{
    		if (m < pm)
    		   return false;
    		else if(d < pd)
    		   return false;
    	}
    	
    	return true;
    }  
    
       //该方法也用于判断时间，你选择的是有效的或无效的
    private boolean isTimeValid(String year1,String month1,String day1,
                                String year2,String month2,String day2)
    {
    	int y1 = Integer.parseInt(year1);
    	int m1 = Integer.parseInt(month1);
    	int d1 = Integer.parseInt(day1);
    	
    	int y2 = Integer.parseInt(year2);
    	int m2 = Integer.parseInt(month2);
    	int d2 = Integer.parseInt(day2);
    	
    	if (y1 < y2)
    	   return true;
    	else if (y1 == y2)
    	{
    		if (m1 < m2)
    		   return true;
    		else if (m1 == m2)
    		{
    			if (d1 < d2)
    			   return true;
    			else if (d1 == d2)
    			   return true;
    			else 
    			   return false;
    		}
    		else 
    		   return false;
    	}
    	else 
    	   return false;
    }    
    
       //用来将指定日期改为工作日的方法。
    private String timeToWeek(String year,String month,String day)
    {
       int sum=0;
       int y = Integer.parseInt(year);
       int m = Integer.parseInt(month);
       int d = Integer.parseInt(day);
              
       int[] dayOfMonth = {0,31,28,31,30,31,30,31,31,30,31,30,31};  
      
          //计算首先指定每年的这一天是星期几
       int firstDayOfYear = firstDay(y);
            
       for(int i = 1;i < m;i++)
        {
           sum=sum+dayOfMonth[i];
        }
      
       sum = sum+(d-1)+firstDayOfYear;

          //如果月份是二月，指定年份是闰年，总天数应加1

       if( (m >= 2) && ((y%4 == 0 && y%100 != 0) || (y%400 == 0)))
          sum ++;
          
       int week = 0;  
          //指定日的工作日是：
       int x = sum % 7;       
       switch(x)
         {
          case 1:
             week = 1;
             break;            
          case 2:
             week = 2;
             break;
          case 3:
             week = 3;
             break;
          case 4:
             week = 4;
             break;
          case 5:
             week = 5;
             break;
          case 6:
             week = 6;
             break;
          case 0:
             week = 7;
             break;
         } 
         
       return String.valueOf(week);                  	    
    }
    
       //该方法用来计算首先指定每年的这一天是星期几
    private int firstDay(int year)
    {
    	int a,b;
    	
	    if(year <= 2000)
	    {
	        a=2000-year;
	        b=6-(a+a/4-a/100+a/400)%7;
	        return b;
	    }
	    else 
	    {
	        a=year-2000;
	        b=(a+1+(a-1)/4-(a-1)/100+(a-1)/400)%7+6;
	        return b%7;
	    }
    }
    
       //单向查询模式的查询方法
    public void executeSingleQuery()
    {
    	String sqlString = formSQLString(start,arrive);    	   
    	         
        ResultSet rs = sqlBean.executeSearch(sqlString);
        
        if (rs != null)
	    {
		       //结果字符串
	        String result = "                                                    " + 
			                "综合查询"; 
			   //根据所给的消息形成特定的结果字符串。     
	        result += formResult(rs,leaveYear,leaveMonth,leaveDay,leaveWeek,start,arrive);    	
	        
	           //在对话框中显示结果
	        showResult(result);
	    }	       
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);           
    }
    

    public void executeDoubleQuery()
    {
    	  
    	String sqlString1 = formSQLString(start,arrive);        
        ResultSet rs1 = sqlBean.executeSearch(sqlString1);
        
        String sqlString2 = formSQLString(arrive,start);    	         
        ResultSet rs2 = sqlBean.executeSearch(sqlString2);
        
        if ( (rs1 != null) || (rs2 != null))
	    {
	    	String result = "                                                  " + 
		                    "综合查询 ";
			   //式生成结果字符串              
	        result += formDoubleResult(rs1,rs2);    	
	        
	        showResult(result);
	    }
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);       
    }
    
       //多方式查询模式查询
    public void executeMutipleQuery()
    {
    	   //输出和返回的方法必须查询数据库两次才能找到
    	   //航班信息从开始在目的地城市 
    	   //从中途到达终点
    	String sqlString1 = formSQLString(start,firstArrive);  	         
        ResultSet rs1 = sqlBean.executeSearch(sqlString1);
        
        String sqlString2 = formSQLString(firstArrive,arrive); 	         
        ResultSet rs2 = sqlBean.executeSearch(sqlString2);
        
        if ((rs1 != null) || (rs2 != null))
	    {
	    	String result = "                                                               " + 
		                    "综合查询                                                 ";
			   //形成多路查询模式的结果字符串。
	        result += formMutipleResult(rs1,rs2);    	
	        
	        showResult(result);
	    }
	    else 
	       JOptionPane.showMessageDialog(null,"没有连接上数据库!",
	                                    "错误信息",JOptionPane.ERROR_MESSAGE);        
    }
    
    public String formSQLString(String begin,String end)
    {
    	String sqlString = "SELECT DISTINCT * FROM " + "Plane " +    	
						   "WHERE start=" + "\'" + begin + "\'" + " AND " +
			               "destination=" + "\'" + end + "\'";			           	
    	                
    	if (!airFirm.equals("所有"))
    	   sqlString += " AND " + "airFirm=" + "\'" + airFirm + "\'";
    	   
    	return sqlString;
    }
    
       //从结果集中获取结果字符串
    public String formResult(ResultSet rs,String year,String month,String day,
                                          String week,String begin,String end)
    {		
		String result = "";
		   //将英语工作日改为中文工作日
		String weekDay = dayOfWeek(week);
		
		result += "\n" + "航程:" + year + "年" + month + "月" + day + "日" +	
		          "(星期" + weekDay + ")  " + begin + "----" + end + "\n"; 
		                  
		result += "航班号    航空公司          起飞地点  抵达地点  起飞时间  抵达时间  " + 
                "儿童票价   成人票价   折扣    班期 " + "\n";
		     
		   //用于确定是否没有记录。         
		int originLength = result.length();
		
		String time1,time2;
		String childFare,adultFare,discount1,discount2,seat;
		
		try
		{	
		    String tempResult = "";
		    String tempWeek;
			while(rs.next())
			{			
				tempResult = rs.getString("flight") + "    "+ rs.getString("airfirm") +"           "+ rs.getString("start") + 
						"         "+rs.getString("destination");
				             
				  
				time1 = rs.getString("leaveTime");
				time2 = rs.getString("arriveTime");
				  
				time1 = getTime(time1);
				time2 = getTime(time2);
				
				tempResult += "        "+time1 + "        "+ time2  + "      ";
				
				childFare = String.valueOf(rs.getFloat("childFare"));
				adultFare = String.valueOf(rs.getFloat("adultFare"));
				discount1 = String.valueOf(rs.getFloat("discount1"));
				discount2 = String.valueOf(rs.getFloat("discount2"));
				seat = String.valueOf(rs.getInt("seat"));
				
				 
				while(childFare.length() != 11)
				   childFare += "  ";
				while(adultFare.length() != 11)
				   adultFare += "  ";
				while(discount1.length() != 8)
				   discount1 += "  ";						
				   
				tempWeek = rs.getString("week1");
		        tempResult += childFare + adultFare + discount1 +
				              tempWeek;
				tempResult += "\n";
				
				   //如果航班时刻表包含用户指定的日期，
				   //记录的是只有一个发现
				   //如果不是，那不是结果
				if (tempWeek.indexOf(week) != -1)
				   result += tempResult;							
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		   //意味着没有发现记录 所以，给用户的信息，找不到相关的信息
		if (result.length() == originLength)
		{
			result += "                                                    " +
			          "对不起,找不到你想要的航班信息!" + "\n";
		}	
		
		return result;
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
	 
      
    private String dayOfWeek(String weekNum)
    {
    	String week = "";
    	int num = Integer.parseInt(weekNum);
    	
		switch(num)
		{
			case 1:
             week = "一";
             break;            
          case 2:
             week = "二";
             break;
          case 3:
             week = "三";
             break;
          case 4:
             week = "四";
             break;
          case 5:
             week = "五";
             break;
          case 6:
             week = "六";
             break;
          case 7:
             week = "日";
             break;
		}
		
		return week;
    }   
    
      
    public String formDoubleResult(ResultSet rs1,ResultSet rs2)
    {
    	String result1 = formResult(rs1,leaveYear,leaveMonth,leaveDay,leaveWeek,start,arrive);
    	String result2 = formResult(rs2,backYear,backMonth,backDay,backWeek,arrive,start);
    	
    	String result = result1 + result2;
    	return result;
    }
    
      
    public String formMutipleResult(ResultSet rs1,ResultSet rs2)
    {
    	String result1 = formResult(rs1,leaveYear,leaveMonth,leaveDay,leaveWeek,start,firstArrive);
    	String result2 = formResult(rs2,leaveYear2,leaveMonth2,leaveDay2,leaveWeek2,firstArrive,arrive);
    	
    	String result = result1 + result2;
    	return result;
    }
    
       //在对话框中显示结果
    public void showResult(String result)
    {
    	JOptionPane.showMessageDialog(null,result,"查询结果",JOptionPane.PLAIN_MESSAGE);
    }         
}