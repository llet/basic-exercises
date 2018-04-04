## 安装gcc和必要库文件

yum -y install make zlib zlib-devel gcc-c++ libtool  openssl openssl-devel

## 安装pcre 

```bash
cd /usr/local/
wget http://downloads.sourceforge.net/project/pcre/pcre/8.35/pcre-8.35.tar.gz
tar zxvf pcre-8.35.tar.gz
cd pcre-8.35
./configure
make && make install
```

## 验证pcre

```bash
pcre-config --version
```

## 下载 Nginx 

> 到http://nginx.org/

```bash
cd /usr/local/
wget http://nginx.org/download/nginx-1.8.1.tar.gz
tar zxvf nginx-1.8.1.tar.gz
cd nginx-1.8.1
./configure --prefix=/usr/local/webserver/nginx --with-http_stub_status_module --with-http_ssl_module --with-pcre=/usr/local/src/pcre-8.35
make && make install
```

## 验证Nginx

```bash
/usr/local/webserver/nginx/sbin/nginx -v
```

## 创建www用户组

```bash## 
groupadd www
useradd -g www www
```

## 修改nginx.conf

```json
user  root;
http { 
    upstream  myserver #配置集群
    {
        server  127.0.0.1:18080  weight=1;
        server  127.0.0.1:28080  weight=2;
    }
  
    server {
        listen       8888;
        server_name demo.com  #监听HOST为demo.com的请求
        location / { 
            proxy_pass http://myserver;
        }
    }
}
```

## Nginx命令

```bash
./nginx				#启动
./nginx -s reload	#重载配置
./nginx -s reopen	#重启
./nginx -s stop 	#停止
```