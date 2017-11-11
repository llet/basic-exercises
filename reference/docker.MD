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


创建一个web demo并打包成镜像  
```
docker pull node
```
- server.js  
```
var http = require('http');
http.createServer(function (request, response) {
    response.writeHead(200, {'Content-Type': 'text/plain'});
    response.end('Hello World\n');
}).listen(8888);
console.log('Server running at http://127.0.0.1:8888/');
```
- Dockerfile  
```
FROM node
RUN mkdir -p /usr/app
WORKDIR /usr/app
ADD . /usr/app
EXPOSE 8888
CMD [ "node", "server.js" ]
```
```
docker build -t web-demo .
docker run -p 8888:8888 web-demo &
curl localhost:8888
```
