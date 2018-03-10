package 航空.帮助;

import 航空.帮助.*;

import javax.swing.*;
import java.io.*;
import java.sql.*;

public class SeatInfo 
{
	private SqlBean sqlBean = new SqlBean();
	private RandomAccessFile raf;
	private final int FLIGHT_PER_DAY = 10;

	public SeatInfo()
	{
            File file=new File(".","data");
        	file.mkdir();
        	File f = new File(file,"座位信息.txt");           
		
		try
		{
			raf = new RandomAccessFile(f,"rw");
			
			if (raf.length() == 0)
			{
				raf.setLength(31 * 4 * FLIGHT_PER_DAY);	
				for (int i = 0 ; i < 31 * FLIGHT_PER_DAY;i++)
			       raf.writeInt(0);
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
	}
	
	public boolean isFull(String flightNum,String day)
	{
	    try
	    {
	     	long index = cacuIndex(day);
		    long address = cacuAddr(flightNum);
		    long absoluteAddress = index + address;
	    
		    raf.seek(absoluteAddress);
		    int bookedSeats = raf.readInt();
	       	String sqlString = "select seat from Plane where flight='" + flightNum + "'";
			ResultSet rs = sqlBean.executeSearch(sqlString);
			
			int totalSeats = 0;	
				
			while (rs.next())
				totalSeats = rs.getInt(1);	
			if (totalSeats == bookedSeats)
			   return true;
			else
			   return false;
	    
	    }
	    catch(Exception e)
	    {
	       	return false;
	    }		
	}
	
	public int dingPiao(String flightNum,String day,int seats)
	{
		int leftSeats = 0;
		try
	    {
	    	long index = cacuIndex(day);
		    long address = cacuAddr(flightNum);
		    long absoluteAddress = index + address;
		    
		    raf.seek(absoluteAddress);
		    int bookedSeats = raf.readInt();
	    
	    	String sqlString = "select seat,week1 from Plane where flight='" + flightNum + "' ";
			ResultSet rs = sqlBean.executeSearch(sqlString);
			
			int totalSeats = 0;	
			String week="";	
			while (rs.next())			   
		   	{
		   		totalSeats = rs.getInt(1);
		   		week=rs.getString(2);
		   	}	
			
			//System.out.println("seat"+totalSeats);
			//System.out.println("week"+week);
			    
			String c=isAbsence(day);
		    int flag=0;
			
			//如果选择的日期对应的星期几，和航班对应的星期几相同，可以订票
			if(c.equals(week)) 
			{
				flag=1;
				 
			}
			
			
			if(flag==1)
			{
				leftSeats = totalSeats - bookedSeats;
			
				if (leftSeats >= seats)
				{
					raf.seek(absoluteAddress);
					raf.writeInt(bookedSeats + seats);
					return -1;
				}
				else
				    return leftSeats;
			}
			else
			     return -2;
						   
			
	    }
	    catch(Exception e)
	    {
	       e.printStackTrace();
	    }
	    
	    return leftSeats;
	}
	
	public void tuiPiao(String flightNum,String day,int seats)
	{
         try
         {
         	long index = cacuIndex(day);
		    long address = cacuAddr(flightNum);
		    long absoluteAddress = index + address;
		    
		    raf.seek(absoluteAddress);
		    int bookedSeats = raf.readInt();
		   
		    if (bookedSeats < seats)
		       JOptionPane.showMessageDialog(null,"退票数大于已定票数!",
		                                     "错误信息",JOptionPane.ERROR_MESSAGE);
		    else
		    {
		    	raf.seek(absoluteAddress);
		    	raf.writeInt(bookedSeats - seats);
		    }
         }  
         catch(Exception e)
         {
         }            
	}
	
	public long cacuIndex(String day)
	{
		String d = day.substring(6,8);
		long index = Long.parseLong(d);
		
		return (index - 1) * 4 * FLIGHT_PER_DAY;
	}
	
	public long cacuAddr(String flightNum)
	{
		
		//flightNum="AC002";
		if(flightNum=="")
		{
			System.out.println("航班号为空");
			return 0;
		}
		
		//System.out.println("航班号"+flightNum);
		
		long remark = 0;
		try 
		{ 
			String sqlString = "select remark from Plane where flight='" + flightNum + "'";
			ResultSet rs = sqlBean.executeSearch(sqlString); 
					
			while (rs.next()) 
			   remark = rs.getInt(1);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (remark - 1) * 4;
	}
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
    
    private String isAbsence(String date)
    {
    	String year=date.substring(0,4);
    	String month=date.substring(4,6);
    	String day=date.substring(6,8);
    	
    	String week=timeToWeek(year,month,day);
    	
    	return week;
    	
    }
}