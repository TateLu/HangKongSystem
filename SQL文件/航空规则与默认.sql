use HangKongDB
GO
create rule pri_rule
as @pri>0
GO
exec sp_bindrule 'pri_rule' , 'Plane.P_price'    --票价

GO
create rule age_rule
as @age>=1 and @age<=110
GO
exec sp_bindrule 'age_rule' , 'Users.U_age'      --年龄
exec sp_bindrule 'age_rule' , 'Header.H_age'      --年龄

GO
create rule seat_rule
as @seat>=0
GO
exec sp_bindrule 'seat_rule' , 'Plane.P_seat'    --票数

GO
create default sex_defa
as '男'
GO
exec sp_bindefault sex_defa,'Users.U_sex'        --性别默认
exec sp_bindefault sex_defa,'Header.H_sex'        --性别默认