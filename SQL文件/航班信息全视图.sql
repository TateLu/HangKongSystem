use HangKongDB
GO

--机票信息查询
create view JP(航线编号,客机类型,出发城市,抵达城市,出发年,出发月,出发日,出发时间,抵达年,抵达月,抵达日,抵达时间,头等舱座位数,头等舱票价,经济舱座位数,经济舱票价,航空公司)
as select Way.W_no,
          P_type,
          W_Start_City,
          W_Arrive_City,
          W_stayear,
          W_stamon,
          W_staday,
          W_Start_Time,
          W_ariyear,
          W_arimon,
          W_ariday,
          W_Arrive_Time,
          P_firseat,
          P_firprice,
          P_ecoseat,
          P_ecoprice,
          F_name
from Book,Way,Firm,Plane
where Book.W_no=Way.W_no and 
      Book.F_no=Firm.F_no and
      Book.P_no=Plane.P_no
      
      
GO
--订单信息查询
create view DP(航线编号,姓名,座位号,出发年,出发月,出发日,出发时间,抵达年,抵达月,抵达日,抵达时间,舱位类型)
as select Way.W_no,
          U_na,
          seat_no,
          W_stayear,
          W_stamon,
          W_staday,
          W_Start_Time,
          W_ariyear,
          W_arimon,
          W_ariday,
          W_Arrive_Time,
          seat_na
from Way,Users,Book
where Way.W_no=Book.W_no and
      Users.U_no=Book.U_no
      
GO
--录入航空信息
create view LRHB(航线编号,客机类型,出发城市,抵达城市,出发年,出发月,出发日,出发时间,抵达年,抵达月,抵达日,抵达时间,
                 航空公司,头等舱座位数,头等舱票价,经济舱座位数,经济舱票价)
as select Way.W_no,
          P_type,
          W_Start_City,
          W_Arrive_City,
          W_stayear,
          W_stamon,
          W_staday,
          W_Start_Time,
          W_ariyear,
          W_arimon,
          W_ariday,
          W_Arrive_Time,
          F_name,
          P_firseat,
          W_firprice,
          P_ecoseat,
          W_ecoprice
from PWF,Way,Firm,Plane
where PWF.W_no=Way.W_no and 
      PWF.F_no=Firm.F_no and
      PWF.P_no=Plane.P_no