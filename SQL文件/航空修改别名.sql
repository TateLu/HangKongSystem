use HangKongDB

--订票视图
select Bno as '票号',B_seat_no as '座位号',F_no as '航班号'
from Book

select U_no as '姓名'
from Users

select Flight_day as '出发日期',Start_Time as '出发时间',Arrive_Time as '抵达时间',Start_City as '出发城市',Arrive_City as '抵达城市'
from Flight

--机票视图
select Air_type as '航班机型',EcoClass_seat as '经济舱票数',FirClass_seat as '头等舱票数',BusClass_seat as '公务舱票数'
from Airplane

select Eco_Class_pri as '经济舱价格',Fir_Class_pri as '头等舱价格',Bus_Class_pri as '公务舱价格',Ctype as '座舱类型'
from Cabin

select Firm_na as '航空公司'
from AirFirm