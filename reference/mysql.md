##### 1.阿里云服务器上安装mysql
```
yum -y install mysql mysql-server mysql-devel #安装 mysql 及其组件 mysql-server ，mysql-devel
service mysqld start  #启动 mysql 数据库
chkconfig mysqld on #设置开机启动 mysql 
```
