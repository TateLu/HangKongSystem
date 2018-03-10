use master 
if exists(select*from sysdatabases where name='HangKongDB')
drop database HangKongDB

create database HangKongDB 
on(
name='HangKongDB_data',
filename='C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\HangKongDB_data.mdf',
size=5MB,
filegrowth=5MB
)

log on(
name='HangKongDB_log',
filename='C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\HangKongDB_log.ldf',
size=5MB,
filegrowth=10%
)