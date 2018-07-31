### 

### 预备

[*Node.js* 中文网](https://www.baidu.com/link?url=nRmjRwMo_EYcmaeSJg1n-qB30sLwAQxQs9m0mFFkwdB1Sf1F69a1Udbo8CLnWbUp&wd=&eqid=9fd465280005e177000000065b5dc1c8) npm是随同nodejs一起安装的包管理工具

验证node和npm: `node -v`,`npm -v`

升级npm:  `npm install npm -g`

设置镜像: `npm config set registry https://registry.npm.taobao.org `

查看配置: `npm config list`,npm的配置文件: `~/.npmrc`

命令行指定仓库:`npm --registry https://registry.npm.taobao.org install webpack`

```properties
registry = https://registry.npm.taobao.org 
prefix=C:\Program Files\nodejs\node_global
cache =C:\Program Files\nodejs\node_global
```

npm下代码-D和-S的区别

```
-S就是--save的简写，就行npm默认一个start的字段，你可以不必输入npm run start 而只需输入npm start，这两个效果是一样的。

-D就是--save-dev 这样安装的包的名称及版本号就会存在package.json的devDependencies这个里面，而--save会将包的名称及版本号放在dependencies里面
```



### 搭建步骤

#### 安装cnpm

 `npm i -g cnpm --registry=https://registry.npm.taobao.org` 验证cnpm: `cnpm -v`

#### 安装vue脚手架

 `cnpm i -g vue-cli`, 可能需要管理员权限 ; 验证vue脚手架: `vue -v`

#### 初始化项目

进入工作目录：`cd E:\webspace`

使用脚手架安装项目: `vue init webpack vue-demo`

```
Project name（工程名）:回车
Project description（工程介绍）：回车
Author：作者名
Vue build（是否安装编译器）:回车
Install vue-router（是否安装Vue路由）：回车
Use ESLint to lint your code（是否使用ESLint检查代码，我们使用idea即可）：n
Set up unit tests（安装测试工具）：n
Setup e2e tests with Nightwatch（也是测试相关）：n
Should we run `npm install` for you after the project has been created? (recommended)：选择：No
```

初始化项目: `cd vue-demo&& cnpm i `

运行: `npm run dev`

到这里基本环节已经搭建好了

安装项目依赖

```cmd
#分别是scss支持，ajax工具，element ui，两个兼容包
cnpm i node-sass -D
cnpm i sass-loader -D
cnpm i axios -D
cnpm i element-ui -D
cnpm i babel-polyfill -D
cnpm i es6-promise -D
```

#### 配置idea

```
File - Settings - Languages&Frameworks - JavaScript：修改JavaScript language version为ECMAScript 6，确认

File - Settings - Plugins：搜索vue，安装Vue.js

Run - Edit Configurations...：点击加号，选择npm，Name为Dev，package.json选择你工程中的package.json，Command为run，Scripts为dev,然后就可以直接在idea中运行了。

继续点击加号，选择npm，Name为Build，package.json选择你工程中的package.json，Command为run，Scripts为build,然后就可以直接在idea中打包了。
```

修改/build/webpack.base.conf.js

```js
module.exports = {
  entry: {
    app: './src/main.js'
  },
修改为
module.exports = {
  entry: {
    app: ['babel-polyfill', './src/main.js']
  },
```



修改src/main.js

```js
import 'es6-promise/auto'
import promise from 'es6-promise'
import Api from './api/index.js'
import Utils from './utils/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.prototype.$utils = Utils;
Vue.prototype.$api = Api;
Vue.use(ElementUI);
```



#### 注意

使用static里的文件尽量使用绝对路径，如/static/image/background.png

使用src里的文件则尽量使用相当路径

scss引入方法

```css
<style lang="scss">
  @import "./style/style.scss";
</style>
```

### 文件结构

```js
├── README.md                       // 项目说明文档
├── node_modules                    // 项目依赖包文件夹
├── build                           // 编译配置文件，一般不用管
│   ├── build.js
│   ├── check-versions.js
│   ├── dev-client.js
│   ├── dev-server.js
│   ├── utils.js
│   ├── vue-loader.conf.js
│   ├── webpack.base.conf.js
│   ├── webpack.dev.conf.js
│   └── webpack.prod.conf.js
├── config                          // 项目基本设置文件夹
│   ├── dev.env.js              // 开发配置文件
│   ├── index.js                    // 配置主文件
│   └── prod.env.js             // 编译配置文件
├── index.html                      // 项目入口文件
├── package-lock.json           // npm5 新增文件，优化性能
├── package.json                    // 项目依赖包配置文件
├── src                             // 我们的项目的源码编写文件
│   ├── App.vue                 // APP入口文件
│   ├── assets                      // 初始项目资源目录，回头删掉
│   │   └── logo.png
│   ├── components              // 组件目录
│   │   └── Hello.vue           // 测试组件，回头删除
│   ├── main.js                 // 主配置文件
│   └── router                      // 路由配置文件夹
│       └── index.js            // 路由配置文件
└── static                          // 资源放置目录
```

### 配置src目录

```js
├── App.vue                         // APP入口文件
├── api                             // 接口调用工具文件夹
│   └── index.js                    // 接口调用工具
├── components                      // 组件文件夹，目前为空
├── config                          // 项目配置文件夹
│   └── index.js                    // 项目配置文件
├── frame                           // 子路由文件夹
│   └── frame.vue                   // 默认子路由文件
├── main.js                         // 项目配置文件
├── page                                // 我们的页面组件文件夹
│   ├── content.vue             // 准备些 cnodejs 的内容页面
│   └── index.vue                   // 准备些 cnodejs 的列表页面
├── router                          // 路由配置文件夹
│   └── index.js                    // 路由配置文件
├── style                           // scss 样式存放目录
│   ├── base                        // 基础样式存放目录
│   │   ├── _base.scss          // 基础样式文件
│   │   ├── _color.scss     // 项目颜色配置变量文件
│   │   ├── _mixin.scss     // scss 混入文件
│   │   └── _reset.scss     // 浏览器初始化文件
│   ├── scss                        // 页面样式文件夹
│   │   ├── _content.scss       // 内容页面样式文件
│   │   └── _index.scss     // 列表样式文件
│   └── style.scss              // 主样式文件
└── utils                           // 常用工具文件夹
    └── index.js                    // 常用工具文件
```

### 常见错误

**npm err! Error: connect ECONNREFUSED 127.0.0.1:8087**

解决办法为：`npm config set proxy null`

**Intellij IDEA 安装插件 报 ‘plugin xxxx is incompatible‘ 解决方案**

在离线安装IDEA插件的时候，可能会出现该问题。引起的原因主要就是版本号不一致。

下面介绍下离线安装找到合适的版本号。

1.在IDEA的help->about下查看IDEA的版本

2.在https://plugins.jetbrains.com上下载你需要的插件

3.找到对应版本`COMPATIBILITY`的插件下载 

#### 