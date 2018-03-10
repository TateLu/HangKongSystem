use HangKongDB

if exists(select* from sysobjects where name='Book')       --订票信息
drop table Book

if exists(select* from sysobjects where name='PWF')       --录入航空信息表
drop table PWF

if exists(select* from sysobjects where name='PF')       --录入公司客机信息表
drop table PF

if exists(select* from sysobjects where name='Users')       --客户信息表
drop table Users

if exists(select* from sysobjects where name='Plane')       --客机信息表
drop table Plane

if exists(select* from sysobjects where name='Header')       --公司负责人表
drop table Header

if exists(select* from sysobjects where name='Firm')       --公司信息表
drop table Firm

if exists(select* from sysobjects where name='Way')       --航线信息表
drop table Way



--客户信息表
create table Users(
U_no char(24) constraint Users_prim primary key,                   --账号
U_na varchar(8)  not NULL,                                         --客户姓名
U_pw varchar(20)   not NULL,                                       --密码
U_id char(32)  not NULL unique,                                    --身份证号码,特有,不可重复
U_sex char(2) check (U_sex in('男','女')) not NULL,                --性别，定义只能男或女
U_age int not NULL,                                                --年龄
U_tel varchar(22) not NULL,                                        --联系电话
U_add varchar(50) not NULL,                                        --客户地址
)

insert into Users values('123','123','123','123','男','19','123','123')


--客机信息表
create table Plane(
P_no char(6) constraint Plane_prim primary key,                --客机编号
P_type varchar(6)  not NULL,                                   --客机类型
P_na varchar(12) not NULL,                                     --客机名称
P_firseat int not NULL,                                        --头等舱座位数   >=0

P_ecoseat int not NULL,                                        --经济舱座位数   >=0

flight varchar(20) not NULL,
airfirm varchar(20) not NULL,
start 	varchar(20) not NULL,
destination varchar(20) not NULL,
leaveTime varchar(20) not NULL,
arriveTime varchar(20) not NULL,
childFare float not NULL,
adultFare float not NULL,
discount1 float not NULL,
discount2 float not NULL,
seat int not NULL,
week1 varchar(20) not NULL,
remark int not NULL,					  

)
insert into Plane values('001','商务机','沪0034',30,30,'AC001','九天航空','北京','成都',1022,1639,678,996,23,56,45,'1',2),
						('002','商务机','沪0034',30,30,'AC002','神州航空','上海','北京',1022,1639,678,996,23,56,45,'2',4),
						('003','商务机','沪0034',30,30,'AC003','四川航空','伦敦','成都',1022,1639,678,996,23,56,45,'3',7),
						('004','商务机','沪0034',30,30,'AC004','重庆航空','广州','北京',1022,1639,678,996,23,56,45,'4',4)


--公司信息表
create table Firm( 
F_no char(6)  constraint Firm_prim primary key,          --公司编号
F_name varchar(10)  not NULL,                            --公司名称
F_add varchar(8) not NULL,                               --公司地址
F_tel char(22) not NULL                                 --联系电话
)



--公司负责人
create table Header(
H_no char(6) constraint Header_prim primary key,                                 --负责人编号
H_na char(8) not NULL,                                                           --负责人姓名
H_id char(32)  not NULL unique,                                    --身份证号码,特有,不可重复
H_sex char(2) check (H_sex in('男','女')) not NULL,                --性别，定义只能男或女
H_age int not NULL,                                                --年龄
F_no char(6) constraint Header_Firm foreign key references Firm(F_no) not NULL,  --公司编号
)




--航线信息表
create table Way(
W_no char(6) constraint Flight_prim primary key,          --航线编号
W_Start_City varchar(10) not NULL,                        --出发城市
W_Arrive_City varchar(10) not NULL,                       --抵达城市
W_stayear varchar(8) not NULL,                            --出发年
W_stamon varchar(4) not NULL,                             --出发月
W_staday varchar(4) not NULL,                             --出发日
W_Start_Time varchar(20) not NULL,                        --出发时间
W_ariyear varchar(8) not NULL,                            --抵达年
W_arimon varchar(4) not NULL,                             --抵达月
W_ariday varchar(4) not NULL,                             --抵达日
W_Arrive_Time varchar(20) not NULL,                       --抵达时间
W_firprice int not NULL,                                       --头等舱票价     >0
W_ecoprice int not NULL,                                       --经济舱票价     >0
) 



--公司~航线~客机 表   订票信息表
create table Book(
U_no char(24) constraint Users_Fore foreign key references Users(U_no) unique not NULL,       --账号     唯一
F_no char(6) constraint Flight_Fore foreign key references Firm(F_no) not NULL,               --公司编号
P_no char(6) constraint Plane_Fore foreign key references Plane(P_no) not NULL,               --客机编号
W_no char(6) constraint Way_Fore foreign key references Way(W_no) not NULL,                   --航线编号
constraint Users_Firm_Plane_Way primary key(U_no,F_no,P_no,W_no),
seat_no int not NULL,                                                                --座位号   //自动生成  <=票数
seat_na char(6) check (seat_na in('经济舱','头等舱')) not NULL,                      --舱位类型
B_firseat int check (B_firseat=0 or B_firseat=1) not NULL,                           --头等舱出售座位数   限制0或1
B_ecoseat int check (B_ecoseat=0 or B_ecoseat=1) not NULL,                           --经济舱出售座位数   限制0或1
)


--录入公司客机信息表
create table PF(
F_no char(6) constraint Flight_PF foreign key references Firm(F_no),        --公司编号
P_no char(6) constraint Plane_PF foreign key references Plane(P_no),        --客机编号
constraint Firm_Plane primary key(F_no,P_no)
)



--录入航空信息表
create table PWF(
F_no char(6) constraint Flight_PWF foreign key references Firm(F_no),        --公司编号
P_no char(6) constraint Plane_PWF foreign key references Plane(P_no),        --客机编号
W_no char(6) constraint Way_PWF foreign key references Way(W_no),            --航线编号
constraint Firm_Plane_Way primary key(F_no,P_no,W_no)
)