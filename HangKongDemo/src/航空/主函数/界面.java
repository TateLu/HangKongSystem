package 航空.主函数;

import 航空.查询.*;
import 航空.管理.*;
import 航空.订票.*;
import 航空.退票.退票;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.net.URL;

class 界面  extends JPanel implements ActionListener
{
	private 查询界面 query;
	static 测试数据库  manager;
	private 退票 tuiPiao;
	private 航空 dingPiao;

	private JLabel j1 = new JLabel("航空售票系统");
	private JLabel j2 = new JLabel("--欢迎您的使用");
	private JButton jb查询 = new JButton("查询");
	private JButton jb订票 = new JButton("订票");
	private JButton jb退票 = new JButton("退票");
	private JButton jb退出 = new JButton("退出");
	private JButton jb管理 = new JButton("管理");
	private JButton jb关于 = new JButton("关于");
	public 界面()
	{
		this.setLayout(null);                 //设置没有格式布局
		this.add(jb查询);                       //设置按钮
		this.add(jb管理);
		this.add(jb订票);
		this.add(jb退票);
		this.add(jb退出);
		this.add(jb关于);
		this.add(j1);
		this.add(j2);
		
		j1.setFont(new Font("宋体",1,60));
		j1.setForeground(Color.WHITE);
		j2.setFont(new Font("宋体",1,35));
		j2.setForeground(Color.WHITE);
		jb查询.setFont(new Font("Times",Font.PLAIN,24));      //字体
		jb管理.setFont(new Font("Times",Font.PLAIN,24));
		jb订票.setFont(new Font("Times",Font.PLAIN,24));
		jb退票.setFont(new Font("Times",Font.PLAIN,24));
		jb退出.setFont(new Font("Times",Font.PLAIN,24));
		jb关于.setFont(new Font("Times",Font.PLAIN,24));
		
		j1.setBounds(150,20,600,300);
		j2.setBounds(600,80,600,300);
		jb查询.setBounds(200,350,100,80);
		jb管理.setBounds(200,480,100,80);
		jb退票.setBounds(700,350,100,80);
		jb订票.setBounds(450,350,100,80);
		jb退出.setBounds(700,480,100,80);
		jb关于.setBounds(450,480,100,80);
		
		jb查询.addActionListener(this);     //添加监听器
		jb管理.addActionListener(this);
		jb退票.addActionListener(this);
		jb订票.addActionListener(this);
		jb退出.addActionListener(this);
		jb关于.addActionListener(this);
	}
	
	public void paintComponent(Graphics g){	    //背景图	
		ImageIcon imageIcon = new ImageIcon("image.gif");
		Image image = imageIcon.getImage();
		g.drawImage(image,0,0, this);	
		}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == jb查询)
		{
			query = new 查询界面();
			query.setSize(470,370);
			query.setLocation(750, 350);
			query.setResizable(false);
		    query.setTitle("航班查询系统");
		    query.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    query.setVisible(true);
		 
		}
		   
		else if (e.getSource() == jb管理 )
		{
			String zhangHao = JOptionPane.showInputDialog(null,"请输入你的帐号:",
			                                              "帐号验证",JOptionPane.PLAIN_MESSAGE);
			if (zhangHao == null)
			   return;
			if (!zhangHao.equals("LBYLLH") )
			{
				JOptionPane.showMessageDialog(null,"对不起!你的帐号不正确!",
				                              "帐号错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String password = JOptionPane.showInputDialog(null,"请输入你的密码:",
			                                              "密码验证",JOptionPane.PLAIN_MESSAGE);
			if (password == null )
			    return;
			if (!password.equals("424418"))
			{
				JOptionPane.showMessageDialog(null,"对不起!你的密码不正确!",
				                              "帐号错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			manager = new 测试数据库();
			
			manager.setSize(470,370);
			manager.setLocation(750, 350);
			manager.setResizable(false);
		    manager.setTitle("航班管理系统");
		    manager.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			manager.setVisible(true);
		}
		
		else if (e.getSource() == jb订票)
		{
			dingPiao = new 航空();
			
			dingPiao.setSize(430,300);
			dingPiao.setLocation(750, 350);
			dingPiao.setResizable(false);
			dingPiao.setTitle("国内机票实时速定");
			dingPiao.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);			
			dingPiao.setVisible(true);
		}
		   
		else if (e.getSource() == jb退票)
		{
			tuiPiao = new 退票();
			
			tuiPiao.setSize(470,370);
			tuiPiao.setLocation(750, 350);
			tuiPiao.setResizable(false);
	    	tuiPiao.setTitle("航班在线退票");
	    	tuiPiao.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	    	
	    	tuiPiao.setVisible(true);
		}
		else if (e.getSource()==jb关于){
			String information = "制作人:" + "   李立煌  梁宝莹    " + "\n" +
	  	                         "指导老师: " + "   彭义春老师 " + "\n" + 
	  	                         "时间: " + "   2017-8-2" + "\n" +
	  	                         "地址: " + "   东莞理工学院城市学院";
	  	                 JOptionPane.showMessageDialog(null,information,"关于",JOptionPane.INFORMATION_MESSAGE);
		}
		// TODO 自动生成的方法存根
		else if (e.getSource()==jb退出){
			System.exit(0);
			this.setVisible(false);
		}
			
	}
}