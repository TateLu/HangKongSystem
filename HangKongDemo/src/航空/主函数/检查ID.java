package 航空.主函数;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class 检查ID extends JFrame implements ActionListener
{ 
    private JTextField zhanghaoField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton jbOK = new JButton("确定");
    private JButton jbCancel = new JButton("取消");
    
    private String name = "1011";
    private String pw = "1011";    
   
    
    public 检查ID()
    {
    	Container c = this.getContentPane();
    	
    	c.setLayout(new FlowLayout());
    	c.add(new JLabel("帐号:"));
    	c.add(zhanghaoField);
    	c.add(new JLabel("密码:"));
    	c.add(passwordField);
    	c.add(jbOK);
    	c.add(jbCancel);
    	
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	int x = (int)(d.getWidth()-this.getWidth())/2;
    	int y = (int)(d.getHeight()- this.getHeight())/2;
    	
    	this.setLocation(x,y);
    	
    	this.setSize(180,120);
    	this.setResizable(false);
    	this.setVisible(true);
    	
    	jbOK.addActionListener(this);
    	jbCancel.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	String zhanghao = zhanghaoField.getText().trim();
    	String password = passwordField.getText().trim();    	
	
		if (e.getSource() == jbOK)
		{
			if (zhanghao.length() == 0)
			{
				JOptionPane.showMessageDialog(null,"请输入帐号!","错误信息",JOptionPane.ERROR_MESSAGE);
				zhanghaoField.setText("");
				passwordField.setText("");				
				return;
			}
			
			//System.out.println("zhanghao:"+zhanghao+"   name:"+name);
			
			if (!zhanghao.equals(name) )
			{
				JOptionPane.showMessageDialog(null,"对不起!你的帐号不正确!",
				                              "帐号错误",JOptionPane.ERROR_MESSAGE);
				zhanghaoField.setText("");
				passwordField.setText("");			
				return;
			}
			
			if (password.length() == 0)
			{
				JOptionPane.showMessageDialog(null,"请输入密码!","错误信息",JOptionPane.ERROR_MESSAGE);
				zhanghaoField.setText("");
				passwordField.setText("");				
				return;
			}
			  
			if (!password.equals(pw))
			{
				JOptionPane.showMessageDialog(null,"对不起!你的密码不正确!",
				                              "帐号错误",JOptionPane.ERROR_MESSAGE);
				zhanghaoField.setText("");
				passwordField.setText("");				
				return;
			}
			
			if (zhanghao.equals(name) && password.equals(pw))
			{
				this.setVisible(false);
				this.dispose();
				
				界面.manager = new 航空.管理.测试数据库();
				界面.manager.setSize(470,370);
				界面.manager.setResizable(false);
				界面.manager.setTitle("航班管理系统");
				界面.manager.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				界面.manager.setVisible(true);
			}
		}
		else if (e.getSource() == jbCancel)
		{
			this.setVisible(false);
			this.dispose();		
		}
	
    }
    
    
	
}