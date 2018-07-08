



```
人力资源管理系统 
前后端分离，项目采用SpringBoot+Vue开发。
https://github.com/lenve/vhr
```

### 'webpack-dev-server' 不是内部或外部命令

#### 回答1:

Change access in node_modules directory

```
chmod -R a+rwx ./node_modules
```

#### 回答2:

I resolve this error running `npm run clean` (clean npm) then delete `node_modules` directory from my project structure. After That install dependencies again using `npm install`

Hope this help.

#### 回答3:

workaround: Remove the lock file.

```
rm .\package-lock.json
```

source: <https://github.com/mapbox/node-pre-gyp/issues/298> (floriantraber)

#### 回答4:

first i ran:   npm run clean (even though it came with errors) Then i deleted the node_modules folder and ran: npm install This seems to have solved the problem.

#### 回答5:

Try to reinstall the cli package globally. In my case, I was trying to test a Vue.js tutorial when I get the same error message. The other thing I did was run the vue command again but this time using webpack-simple and that is why I am not sure wich one solved the problem but now it is working.

#### 回答6:

I was getting similar error messages on a 16.04 Ubuntu instance with DigitalOcean while running `npm run build` on an app made with `create-react-app` ([link](https://github.com/facebookincubator/create-react-app)). I upgraded the instance from 512MB RAM to 1GB ($5/mo to $10/mo) and then the script was able to run.

I post this here to point out that you may get this error due to resource limitations, which I didn't really see explained elsewhere on issue pages and SO answers. And nothing I saw in the error logs pointed me in this direction.

#### 回答7:

Check for port availability as well if you encounter below message :

```
Error: listen EACCES 127.0.0.1:8080

at Object._errnoException (util.js:999:13)
at _exceptionWithHostPort (util.js:1020:20)
at Server.setupListenHandle [as _listen2] (net.js:1362:19)
at listenInCluster (net.js:1420:12)
at GetAddrInfoReqWrap.doListen [as callback] (net.js:1535:7)
at GetAddrInfoReqWrap.onlookup [as oncomplete] (dns.js:102:10)
npm ERR! code ELIFECYCLE
npm ERR! errno 1
```

#### 回答8:

run the command as superuser:

```
sudo npm x-command
```

我设置了 prefix = "E:\\node_modules" webpack被安装在了  E:\node_modules 下

需要将这个路径添加到path中去



java -Dvipserver.server.port=8080 -Dpandora.location="E:/Program Files/apache-maven-3.5.0/rep/com/taobao/pandora/taobao-hsf.sar/dev-SNAPSHOT/taobao-hsf.sar-dev-SNAPSHOT.jar"  -jar elina-provider-1.0.1.jar