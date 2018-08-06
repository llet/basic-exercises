## node 的数据类型

```js
typeof 001; //'number'
typeof 'a string'; //'string'
typeof '2'+ 007; //'string7' 
typeof ('2' * 007); //'number'
typeof ('2x' * 007); //'NaN'
typeof notexists; //'undefined'
typeof Object; //'function'
typeof Array; //'function'
typeof RegExp; //'function'
typeof (x=>x*x); //'function'
typeof require; //'function'
typeof new Object(); //'object'
typeof new Array(); //'object'
typeof new RegExp("\\w+"); //'object'
typeof /\w+/; //'object'
typeof module; //'object'
typeof module.exports //'object'
typeof require('./foo.js'); //'object'
typeof export; //Unexpected token export
```

## CommonJS和ES6

CommonJS模块规范：

​	每一个文件就是一个 module object

​	require、module.exports、exports, 这些是函数或者对象

ES6语法：

​	import、export、export default , 这些是ES6关键字

## 常见用法

可以导出的内容包括类、函数以及var、let和const修饰的变量。

### 一、import/export default

每个模块仅有一个default的导出，导出内容可以是一个function、class，object等。因为这种方式被当做主要的导出内容，导入方式最为简单。

```js
// there is no semi-colon here
export default function() {} 
export default class {}

//示例
class A extends Component{
   ...
}
export default A;

//对应的import示例。
import A from './requireTest'

//default export, 输入 lodash 模块
import _ from 'lodash';

//一条import语句中，同时输入默认方法和其他变量
import _, { each } from 'lodash';

//上述代码对应的export语句
export default function (obj) {
  // ···
}
export function each(obj, iterator, context) {
  // ···
}
export { each as forEach };

```

注意：一个模块仅仅只允许导出一个default对象，实际导出的是一个default命名的变量进行重命名，等价语句如下。所以import后可以是任意变量名称，且不需要{}。

```js
import any from './requireTest'
import {default as any } from './requireTest'

```

### 二、named 导入导出

需要特别注意的是，export命令规定的是对外的接口，必须与模块内部的变量建立一一对应关系。另外，export语句输出的接口，与其对应的值是**动态绑定**关系，即通过该接口，可以取到模块内部实时的值。

import命令接受一对大括号，里面指定要从其他模块导入的变量名。大括号里面的变量名，必须与被导入模块（profile.js）对外接口的名称相同。如果想为输入的变量重新取一个名字，import命令要使用as关键字，将输入的变量重命名。

import后面的from指定模块文件的位置，可以是相对路径，也可以是绝对路径，.js路径可以省略。如果只是模块名，不带有路径，那么必须有配置文件，告诉 JavaScript 引擎该模块的位置。

```js
// profile.js
//第一种export
export var firstName = 'Michael';
export function f() {};

//第二种export，优先使用这种写法
var firstName = 'Michael';
export {firstName}；

function f() {}
export {f};

//main.js
import { firstName, f } from './profile';
import { firstName as surname } from './profile';

```

### 三、重命名导入导出

```js
export { myFunction }; // exports a function declared earlier
export const foo = Math.sqrt(2); // exports a constant


```

import不同模块的导出内容时，必须保持命名的唯一性。此时可以用重命名来解决，包括以下两类。

```js
//导出的时候重命名
function v1() { ... }
function v2() { ... }

export {
      v1 as streamV1,
      v2 as streamV2,
      v2 as streamLatestVersion  //可以用两个不同的名称导出相同的值
};


//导入的时候重命名
// 这两个模块都会导出以`flip`命名的东西。同时导入两者，需要将其中一个的名称改掉。
import {flip as flipOmelet} from "eggs.js";
import {flip as flipHouse} from "real-estate.js";

```

### 四、export和import的复合写法

如果在一个模块之中，先输入后输出同一个模块，import语句可以与export语句写在一起。

```js
export { foo, bar } from 'my_module';

// 等同于
import { foo, bar } from 'my_module';
export { foo, bar };
```

## 常见问题

**module.exports**和**exports**

exports变量实际指向module.exports，这等同在每个模块头部，有一行这样的命令： `var exports = module.exports;`

**export**和**export default**

一个是导出一个个单独接口，一个是默认导出一个整体接口。使用`import`命令的时候，用户需要知道所要加载的变量名或函数名，否则无法加载。这里就有一个简单写法不用去知道有哪些具体的暴露接口名，就用`export default`命令，为模块指定默认输出。

**import和require**

import是es6关键字，require是commonJs中的函数，作用都是导入模块。

**lambda**和**function**

在普通函数里常见的`this`、`arguments`、`caller`在lambda里是统统没有的。

```js
function Car(name) {this.name=name;};
new Car('xxx'); //Car { name: 'xxx' }

Car = ()=>{this.name=name;}
new Car() //TypeError: Car is not a constructor
```

