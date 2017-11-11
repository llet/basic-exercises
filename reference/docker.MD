问题|命令
-|-
给docker添加国内加速器|`vi /etc/systemd/system/multi-user.target.wants/docker.service`</br>`ExecStart=/usr/bin/dockerd --registry-mirror=https://jxus37ad.mirror.aliyuncs.com`</br>`sudo systemctl daemon-reload`</br>`sudo systemctl restart docker`
列出当前所有镜像|`docker images`
搜索tomcat镜像|`docker search tomcat`
下载tomcat镜像|`docker pull tomcat`
搜索mongodb镜像|`docker search mongodb`
下载mongodb镜像|`docker pull mongodb`
创建并启动一个test-tomcat容器|`docker run --name test-tomcat -p 80:8080 tomcat &` 
创建并启动一个test-tomcat2容器|`docker run --name test-tomcat2 -p 81:8080 tomcat &` 
创建并启动一个test-tomcat3容器|`docker run --name test-tomcat3 -p 82:8080 tomcat &` 
停止test-tomcat容器|`docker stop test-tomcat`
启动test-tomcat容器|`docker start test-tomcat`
进入test-tomcat容器查看日志|`docker exec -it test-tomcat  /bin/sh` </br> ` cat logs/catalina.*.log`
直接查看test-tomcat容器查看日志|`docker logs test-tomcat`
退出容器|`exit`
查看当前所有容器|`docker ps -a`
删除test-tomcat容器|` docker rm test-tomcat`
删除所有容器|`docker rm $(docker ps -aq)`
删除tomcat镜像|`docker rmi tomcat`
删除所有镜像|`docker rmi $(docker images -aq)`


+ 创建一个web demo并打包成镜像  
    + `docker pull node`
    + `cat server.js`
    ```
    var http = require('http');
    http.createServer(function (request, response) {
        response.writeHead(200, {'Content-Type': 'text/plain'});
        response.end('Hello World\n');
    }).listen(8888);
    console.log('Server running at http://127.0.0.1:8888/');
    ```
    + `cat Dockerfile`
    ```
    FROM node
    RUN mkdir -p /usr/app
    WORKDIR /usr/app
    ADD . /usr/app
    EXPOSE 8888
    CMD [ "node", "server.js" ]
    ```
    + `docker build -t web-demo`
    + `docker run --name web-demo -p 8888:8888 web-demo &`
    + `curl localhost:8888`
    ```
    Hello World
    ```
+ 使用mysql镜像创建mysql-demo服务  

    + `cat start.sh`
    ```
    #!/bin/sh

    echo "Starting mysql-demo..."  
    docker run --name mysql-demo -d \
      -e MYSQL_ROOT_PASSWORD=root \
      -e MYSQL_DATABASE=users \
      -e MYSQL_USER=guests \
      -e MYSQL_PASSWORD=guests \
      -p 3306:3306 \
      mysql:latest
    echo "Waiting for mysql-demo to start up..."  
    docker exec mysql-demo mysqladmin --silent --wait=30 -uguests -pguests ping || exit 1

    echo "Setting up initial data..."  
    docker exec -i mysql-demo mysql -uguests -pguests users <<EOF
    create table directory (user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, email TEXT, phone_number TEXT);  
    insert into directory (email, phone_number) values ('homer@thesimpsons.com', '+1 888 123 1111');  
    insert into directory (email, phone_number) values ('marge@thesimpsons.com', '+1 888 123 1112');  
    insert into directory (email, phone_number) values ('maggie@thesimpsons.com', '+1 888 123 1113');  
    insert into directory (email, phone_number) values ('lisa@thesimpsons.com', '+1 888 123 1114');  
    insert into directory (email, phone_number) values ('bart@thesimpsons.com', '+1 888 123 1115');
    EOF
    ```
    + `cat stop.sh`
    ```
    #!/bin/sh
    docker stop mysql-demo && docker rm mysql-demo
    ```
+ 创建一个web demo查询刚才创建的mysql-demo  


    + `apt-get install npm`
    
    
    + `npm install mysql` #要在server.js所在目录下执行  
    
    
    + `cat server.js`
    ```
    var mysql = require('mysql');
    var connection = mysql.createConnection({
        host: 'localhost',
        user: 'guests',
        password: 'guests',
        database:'users',
        port: 3306
    });
    var http = require('http');
    connection.connect();

    http.createServer(function (request, response) {
        connection.query('select * from `directory`', function(err, rows, fields) {
          if (err) throw err;
          console.log('查询结果为: ', rows);
          response.writeHead(200, {'Content-Type': 'text/plain'});
          response.end(JSON.stringify(rows));
        });
    }).listen(8888);
    console.log('Server running at http://127.0.0.1:8888/');
    ```
