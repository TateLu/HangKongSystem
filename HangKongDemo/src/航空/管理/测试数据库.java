package 航空.管理;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class 测试数据库 extends JFrame
{
	private JTabbedPane tab=new JTabbedPane();
	
	public 测试数据库()
	{
		tab.add("    插   入    ",new 插入面板());
		tab.add("    删   除    ",new 删除面板());
		tab.add("    更   新    ",new 更新面板());
		tab.add("  查 看 数 据 库  ",new 显示());
		//tab.setBorder(new MatteBorder(new ImageIcon("image.gif")));
		this.getContentPane().add(tab);
		
		this.addWindowListener(new WindowAdapter()
	                          {
	                          	public void windowClosing(WindowEvent e)
	                          	{
	                          		测试数据库.this.setVisible(false);
	                          		测试数据库.this.dispose();
	                          	}
	                          }
	                      );	
		
	}
}