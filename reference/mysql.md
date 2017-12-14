##### 1.阿里云服务器上安装mysql
```
#安装 mysql 及其组件 mysql-server ，mysql-devel
yum -y install mysql mysql-server mysql-devel

#启动 mysql 数据库
service mysqld start

#设置开机启动 mysql
chkconfig mysqld on 

#设置mysql root用户密码
mysqladmin -uroot -p password "123456"

#备份test数据库
mysqldump -uroot -p test >test.sql

#将test.sql导入数据库
mysql -uroot -p -Dtest<test.sql

#在'mysql>'命令提示符下将test.sql导入数据库
mysql> source /root/test.sql

```

