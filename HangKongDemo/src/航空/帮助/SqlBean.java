 package 航空.帮助;

import java.io.*;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class SqlBean {
	//定义需要的对象
	PreparedStatement ps=null;
	ResultSet rs=null;
	Connection ct=null;
	String dirverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String URL = "jdbc:sqlserver://localhost:1433;database=HangKongDB";
	 private final String user = "sa";
	 private final String password = "122026";
	//构造函数，初始化ct数据库连接
	 public SqlBean()
		{
			try
			{
				Class.forName(dirverName);
			}
			catch(ClassNotFoundException e)
			{
			}
		}
		   
		   //select
		public ResultSet executeSearch(String sql)
		{
			rs=null;
			try
			{
				
				ct = DriverManager.getConnection(URL,user,password);
				
				Statement stmt=ct.createStatement();
				
				if(stmt==null)
				{
					System.out.println("stmt为空");
				}
				
				rs=stmt.executeQuery(sql);
				
				
				if(ct==null)
				{
					System.out.println("excuteQuery11,连接数据库出错了");
					return null;
				}
			}
			catch(SQLException ex)
			{		
				System.out.println("excuteQuery,连接数据库出错了");
			}
		
			if(rs==null)
			{
				System.out.println("excuteQuery22,连接数据库出错了");
				return null;
			}
			
			return rs;
		}
		   
		   //insert
		public int executeInsert(String sql)
		{
			int num=0;
			try
			{
				ct = DriverManager.getConnection(URL,user,password);
				Statement stmt=ct.createStatement();
				num=stmt.executeUpdate(sql);
			}
			catch(SQLException ex)
			{
				System.out.println("excuteInsert,连接数据库出错了");
			}
			
			return num;
		} 
		   
		   //delete
		public int executeDelete(String sql)
		{
			int num=0;
			try
			{
				ct = DriverManager.getConnection(URL,user,password);
				Statement stmt=ct.createStatement();
				num=stmt.executeUpdate(sql);
			}
			catch(SQLException e)
			{
				System.out.println("excuteDelete,连接数据库出错了");
			}
		
			return num;
		}
		
		   //update
		public int executeUpdate(String sql)
		{
			int num=0;
			try
			{
				ct = DriverManager.getConnection(URL,user,password);
				Statement stmt=ct.createStatement();
				num=stmt.executeUpdate(sql);
			}
			catch(SQLException e)
			{
				System.out.println("excuteUpdate,连接数据库出错了");
			}
		
			return num;
		}
		   
		   //close
		public void CloseDataBase()
		{
			try
			{
				ct.close();
			}
			catch(Exception e)
			{
			}
		}
}