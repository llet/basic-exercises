## 各种登录方案

### 说明

用到的软件有：Chrome、Fiddler

---

### HTTP协议

#### 请求头

```properties
#Host HTTP/1.1新增的头 指定服务器地址 可以是域名+端口、IP+端口，可以为空值但不可以不带
Host: www.jianshu.com 
#Referer 告诉服务器我是从哪个页面链接过来的。
#到底是referer还是referrer，没人能说得清，不过拼写上，后者是正确的
#搞清referer和origin https://blog.csdn.net/zdavb/article/details/51161130
Referer: https://www.jianshu.com/sessions
#跨域的时候get，post都会显示origin，同域的时候get不显示origin，post显示origin
#服务器可以拒绝一切Origin字段为外站的请求
Origin: https://www.jianshu.com
#用来保存一些客户端参数，并在发送请求的时候发送给服务器
Cookie: read_mode=day; default_font=font2; locale=zh-CN; sajssdk_2015_cross_new_user=1; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536407402;
```

#### 响应头

---

### 简书的登录方案

#### 登录前

```properties
POST https://www.jianshu.com/sessions HTTP/1.1
Host: www.jianshu.com
Content-Type: application/x-www-form-urlencoded
Referer: https://www.jianshu.com/sessions
${Cookie}

${Body}
```

登录前已经保存的Cookie字段：

```properties
read_mode=day
default_font=font2
locale=zh-CN
_m7e_session=b77a648169368511a83731b345dc4867
sajssdk_2015_cross_new_user=1
sensorsdata2015jssdkcross={"distinct_id":"165b9047663108-****-9393265-2073600-165b9047664c51","$device_id":"165b9047663108--****--9393265-2073600-165b9047664c51","props":{}}
Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536407402
Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536407413
```

登录时提交的表单字段

```properties
utf8=✓
authenticity_token=nr23a0EFPRtNtxtYaQxsOhYayH53PQshlbx1Ph+ezb21a4IBk2tIQHnr8rgc9kVYqAKb0ihBT4Z5s6yuaqZLow==
session[email_or_mobile_number]=***********
session[password]=********
session[oversea]=false
captcha[validation][challenge]=4c2358c1cc9a9f92dcb4dcaa67a386dc
captcha[validation][gt]=ec47641997d5292180681a247db3c92e
captcha[validation][validate]=75a7e00ab99fc8d904376a4048529a39
captcha[validation][seccode]=75a7e00ab99fc8d904376a4048529a39|jordan
session[remember_me]=true
```

登录表单中有一个隐藏标签

```html
<input type="hidden" name="authenticity_token" value="nr23a0EFPRtNtxtYaQxsOhYayH53PQshlbx1Ph+ezb21a4IBk2tIQHnr8rgc9kVYqAKb0ihBT4Z5s6yuaqZLow==">
```

可以判断`authenticity_token`是服务器生成页面`https://www.jianshu.com/sign_in`时一并返回的随机数

captcha相关的字段应该都是验证码相关的，不必关注

session[oversea]是否海外登录，不必关注

所以登录请求主要的参数有：`authenticity_token`,`email_or_mobile_number`,`password`

下面是登录的响应

```properties
Set-Cookie: locale=zh-CN; path=/
Set-Cookie: remember_user_token=W1s4NDU4NDI3XSwiJDJhJDExJHgwSHZrcDZ5T1NoWllnbXhxd01CdE8iLCIxNTM2NDA3NDI3LjUwMTI5ODciXQ%3D%3D--531d073044a66cee41df1d9369aded16beda6a3e; path=/; expires=Sat, 22 Sep 2018 11:50:27 -0000; secure; HttpOnly
Set-Cookie: _m7e_session=6c16cb8889d24fd54297bd36d7dc8e5b; path=/; expires=Sat, 08 Sep 2018 17:50:27 -0000; secure; HttpOnly
```

#### 登录后

Cookie变为

```properties
read_mode=day
default_font=font2
locale=zh-CN
sajssdk_2015_cross_new_user=1
Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536407402,1536410790,1536410862
remember_user_token=W1s4NDU4NDI3XSwiJDJhJDExJHgwSHZrcDZ5T1NoWllnbXhxd01CdE8iLCIxNTM2NDA3NDI3LjUwMTI5ODciXQ==--531d073044a66cee41df1d9369aded16beda6a3e
_m7e_session=6c16cb8889d24fd54297bd36d7dc8e5b
sensorsdata2015jssdkcross={"distinct_id":"8458427","$device_id":"165b9047663108-034739248070d7-9393265-2073600-165b9047664c51","props":{"$latest_traffic_source_type":"自然搜索流量","$latest_referrer":"https://www.baidu.com/link","$latest_referrer_host":"www.baidu.com","$latest_search_keyword":"未取到值"},"first_id":"165b9047663108-034739248070d7-9393265-2073600-165b9047664c51"}
Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536411998
```

访问登录页面时会生成新的`_m7e_session`

对比登录前后可以发现以下字段发生了变化

```properties
Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536407402,1536410790,1536410862
remember_user_token=W1s4NDU4NDI3XSwiJDJhJDExJHgwSHZrcDZ5T1NoWllnbXhxd01CdE8iLCIxNTM2NDEwOTY0LjE0OTcyOTUiXQ==--778dd5057d181496705c684cbfb1db74eac1a66d
_m7e_session=b094dd5c83db55a54d0b9299e71a144e
sensorsdata2015jssdkcross={"distinct_id":"8458427","$device_id":"165b9047663108-034739248070d7-9393265-2073600-165b9047664c51","props":{"$latest_traffic_source_type":"自然搜索流量","$latest_referrer":"https://www.baidu.com/link","$latest_referrer_host":"www.baidu.com","$latest_search_keyword":"未取到值"},"first_id":"165b9047663108-034739248070d7-9393265-2073600-165b9047664c51"}
Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1536411998
```

#### 分析

`sensorsdata2015jssdkcross`应该是用来数据的统计和分析的，不必关注

`Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068`和`Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068`保存的是以秒为单位的时间，应该也不是很重要，不必关注

现在还剩下`_m7e_session`和`remember_user_token`

这两个字段每次登录的时候都会生成不同的值

~~`_m7e_session` 就是一个32字符长度的字符串，在Chrome控制台中删除 `_m7e_session`后并没有要求重新登录，可以判断后台代码并没有处理`_m7e_session`这个值，这个值应该是类似`tomcat`的服务器或者框架来自动处理的，没有什么用，不必关注。~~ (说明：后来又做了一些测试，发现这个结论是错误的)

案例1：登录成功后访问新建专题页面，删除`_m7e_session`，刷新页面，结果生成新的`_m7e_session`，未要求重新登录

案例2：登录成功后访问新建专题页面，删除`remember_user_token`，刷新页面，结果cookie中`remember_user_token`被删除，`_m7e_session`还在，结果未要求重新登录

案例3：登录成功后访问新建专题页面，删除`remember_user_token`和`_m7e_session`，刷新页面，结果跳转到登录页。（这里将`remember_user_token`的键值对保存备用）

案例4：案例3的基础上，添加cookie `remember_user_token`，刷新页面，发现已经登录。参考：chrome添加cookie 

 得到新结论是：`_m7e_session`是保存在内存中的会话，`remember_user_token`是保存在数据库中的会话，简书使用的是**session 会话机制**  参考：Session & JWT-Auth



#### 参考

简书的session https://www.jianshu.com/p/b183f5823523?utm_source=oschina-app

Token认证机制 https://www.cnblogs.com/xiekeli/p/5607107.html

Session & JWT-Auth https://www.jianshu.com/p/760cc0721dea

chrome添加cookie https://blog.csdn.net/hekewangzi/article/details/72763577