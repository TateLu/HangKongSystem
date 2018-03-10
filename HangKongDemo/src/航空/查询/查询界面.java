package 航空.查询; 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class 查询界面 extends JFrame implements ActionListener,WindowListener
{	
	public 查询界面()
	{
		JTabbedPane jtp = new JTabbedPane();
		jtp.add(" 普 通 查 询 ",new 普通查询());
		jtp.add(" 综 合 查 询",new 综合查询());
		
		jtp.setBorder(new MatteBorder(new ImageIcon("f.gif")));
		this.getContentPane().add(jtp);
		
		this.addWindowListener(new WindowAdapter()
		                          {
		                          	public void windowClosing(WindowEvent e)
		                          	{
		                          		查询界面.this.setVisible(false);
		                          		查询界面.this.dispose();
		                          	}
		                          }
		                      );		
	}
	
	public void actionPerformed(ActionEvent e)
	{
	}
	
	   //实现窗口侦听器的抽象方法
	    
	public void windowClosing(WindowEvent e)
	{	
	    closeDataBase();	
	}
	
	   //退出程序时，数据库应该关闭。为了避免占用计算机的资源
	public void closeDataBase()
	{
		航班查询.sqlBean.CloseDataBase();
		航空公司查询.sqlBean.CloseDataBase();
		目的地查询.sqlBean.CloseDataBase();
		综合查询.sqlBean.CloseDataBase();
	}
	
	public void windowClosed(WindowEvent e)
	{		
	}
	
	public void windowOpened(WindowEvent e)
	{		
	}
	
	public void windowIconified(WindowEvent e)
	{		
	}
	
	public void windowDeiconified(WindowEvent e)
	{		
	}
	
	public void windowDeactivated(WindowEvent e)
	{		
	}
	
	public void windowActivated(WindowEvent e)
	{		
	}	

}