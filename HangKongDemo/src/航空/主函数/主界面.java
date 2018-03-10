package 航空.主函数;

import 航空.帮助.*;
import 航空.帮助.UpdateComboBox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class 主界面 extends JFrame
{   
    private UpdateComboBox update;
    private 界面 jiemian;
	public 主界面()
	{
		update = new UpdateComboBox();
		
		jiemian = new 界面();
		
		this.getContentPane().add(jiemian);		
	}
	
	public static void main(String args[])
	{
		   //获取系统对GUI的查找
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
		}
	  
		主界面 frame =new 主界面();
		frame.setSize(1030,720);
		Dimension screenSize=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=frame.getSize();
		frame.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2 );
		frame.setResizable(false);
		frame.setTitle("航空售票管理系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}