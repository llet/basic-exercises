```bash
[admin@localhost Desktop]$ uname -a
Linux localhost.localdomain 2.6.32-696.el6.x86_64 #1 SMP Tue Feb 21 00:53:17 EST 2017 x86_64 x86_64 x86_64 GNU/Linux
[admin@localhost Desktop]$ cat /proc/version
Linux version 2.6.32-696.el6.x86_64 (mockbuild@x86-027.build.eng.bos.redhat.com) (gcc version 4.4.7 20120313 (Red Hat 4.4.7-18) (GCC) ) #1 SMP Tue Feb 21 00:53:17 EST 2017
[admin@localhost Desktop]$ cat /etc/issue
Red Hat Enterprise Linux Server release 6.9 (Santiago)
Kernel \r on an \m
```

```bash
sudo rpm -ivh docker-io-1.7.1-2.el6.x86_64.rpm
sudo rpm -ivh lua-alt-getopt-0.7.0-1.el6.noarch.rpm
sudo rpm -ivh lua-filesystem-1.4.2-1.el6.x86_64.rpm
sudo rpm -ivh lua-lxc-1.0.11-1.el6.x86_64.rpm
sudo rpm -ivh lxc-1.0.11-1.el6.x86_64.rpm
sudo rpm -ivh lxc-libs-1.0.11-1.el6.x86_64.rpm
```

开机启动docker服务

```bash
chkconfig  docker on
```

让docker 容器开机自动启动

```bash
docker run --restart=always
docker update --restart=always
```

docker保存本地镜像

```bash
docker save spring-boot-docker  -o  /home/wzh/docker/spring-boot-docker.tar
```

docker加载镜像

```bash
docker load -i spring-boot-docker.tar  
```

报错

```bash
[admin@localhost Desktop]$ docker images
Get http:///var/run/docker.sock/v1.19/images/json: dial unix /var/run/docker.sock: permission denied. Are you trying to connect to a TLS-enabled daemon without TLS?
```

```
sudo groupadd docker 
usermod -aG dockerroot ${user}
usermod -aG docker ${user}

newgrp - docker
```

docker配置国内镜像

```bash
#如果你是redhat6.5 并且docker版本1.7.1就这么配置镜像源
#/etc/sysconfig/docker
other_args=="--registry-mirror=https://docker.mirrors.ustc.edu.cn"

#如果你是docker1.8 或者docker1.10等更高版本，你应该这么配置
#/etc/docker/daemon.json

```

Docker 1.7.1版本pull私有镜像仓库报错，V1，V2等

https://blog.csdn.net/gezilan/article/details/79398832

```
[root@123 ] # vi /etc/init.d/docker
## 找到exec，直接添加--insecure-registry ip:port
prog="docker"
exec="/usr/bin/$prog --insecure-registry ip:port"
pidfile="/var/run/$prog.pid"
lockfile="/var/lock/subsys/$prog"
logfile="/var/log/$prog"
```

配置yum源

```
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo
yum clean all
yum makecache
```



上传镜像到私有仓库

```bash
#!/bin/bash
# This script will upload the given local images to a registry server ($registry is the default value).
# Usage: push_images image1 [image2...]
# Author: Mongo
# Create: 2016-05-26
#The registry server address where you want push the images into(Please instead of your private registry's domain name)
registry=192.168.72.128:5000
### DO NOT MODIFY THE FOLLOWING PART, UNLESS YOU KNOW WHAT IT MEANS. YOU CAN ALSO LEARN IT FROM HERE.###
echo_g () {
    [ $# -ne 1 ] && return 0
    echo -e "\033[32m$1\033[0m"
}
echo_b () {
    [ $# -ne 1 ] && return 0
    echo -e "\033[34m$1\033[0m"
}
usage() {
    sudo docker images
    echo "Usage: $0 registry1:tag1 [registry2:tag2...]"
}
[ $# -lt 1 ] && usage && exit
echo_b "The registry server is $registry"
for image in "$@"
    do
        echo_b "Uploading $image..."
        sudo docker tag $image $registry/$image
        sudo docker push $registry/$image
        sudo docker rmi $registry/$image
        echo_g "Push $image success!"
    done
```

批量上传镜像到私有仓库

```bash
#!/bin/sh
# This script will upload all local images to a registry server ($registry is the default value).
# Usage: push_all
# Author: Mongo
# Create: 2016-05-26
for image in `sudo docker images|grep -v "REPOSITORY"|grep -v "<none>"|awk '{print $1":"$2}'` 
do
    ./push.sh $image
done
```

下载镜像脚本 

```bash
#!/bin/bash

# This script will upload the given local images to a registry server ($registry is the default value).
# Usage:  push_images image1 [image2...]
# Author: Mongo
# Create: 2014-05-26

#The registry server address where you want push the images into
registry=192.168.72.128:5000

### DO NOT MODIFY THE FOLLOWING PART, UNLESS YOU KNOW WHAT IT MEANS ###
echo_g () {
    [ $# -ne 1 ] && return 0
    echo -e "\033[32m$1\033[0m"
}

echo_b () {
    [ $# -ne 1 ] && return 0
    echo -e "\033[34m$1\033[0m"
}

usage() {
    sudo docker images
    echo "Usage: $0 registry1:tag1 [registry2:tag2...]"
}

[ $# -lt 1 ] && usage && exit

echo_b "The registry server is $registry"

for image in "$@"
    do
        echo_b "Downloading $image..."
        sudo docker pull $registry/$image
        sudo docker tag $registry/$image $image
        sudo docker rmi $registry/$image
        echo_g "Download $image Success!"
    done
```

执行脚本

```bash
chmod +x ./push.sh ./pushall.sh ./pull.sh
# 上传指定镜像(可上传多个，中间用空格隔开)
./push.sh ImageName[:TagName] [ImageName[:TagName] ···]
# 例如：./push.sh busybox:latest ubutnu:14.04

# 上传所有镜像
./pushall.sh

# 下载指定镜像(可上传多个，中间用空格隔开)
./pull.sh ImageName[:TagName] [ImageName[:TagName] ···]
# 例如：./pull.sh busybox:latest ubutnu:14.04
```

上传镜像失败

```
Get https://192.168.72.128:5000/v1/_ping: tls: oversized record received with length 20527. If this private registry supports only HTTP or HTTPS with an unknown CA certificate, please add `--insecure-registry 192.168.72.128:5000` to the daemon's arguments. In the case of HTTPS, if you have access to the registry's CA certificate, no need for the flag; simply place the CA certificate at /etc/docker/certs.d/192.168.72.128:5000/ca.crt
Untagged: 192.168.72.128:5000/registry:latest
```

```bash
vi /etc/sysconfig/docker

# Other arguments to pass to the docker daemon process
# These will be parsed by the sysv initscript and appended
# to the arguments list passed to docker -d
other_args="--selinux-enabled=true --insecure-registry 192.168.220.123:5000"  #修改处
DOCKER_CERT_PATH=/etc/docker
# Resolves: rhbz#1176302 (docker issue #407)
DOCKER_NOWARN_KERNEL_VERSION=1
```

sudo 出现 is not in the sudoers file

```
sudo vim /etc/sudoers

${user} ALL=(ALL) ALL 
%${user} ALL=(ALL) ALL 
${user} ALL=(ALL) NOPASSWD: ALL 
%${user} ALL=(ALL) NOPASSWD: ALL
```

第一行:允许用户youuser执行sudo命令(需要输入密码). 
第二行:允许用户组youuser里面的用户执行sudo命令(需要输入密码). 
第三行:允许用户youuser执行sudo命令,并且在执行的时候不输入密码. 
第四行:允许用户组youuser里面的用户执行sudo命令,并且在执行的时候不输入密码.