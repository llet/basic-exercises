redis 官网 ：https://redis.io/

redis http://try.redis.io/

Linux 版本: https://redis.io/download

Window 版本: https://github.com/MicrosoftArchive/redis/releases

## Window 安装 redis

---

从github上下载**Redis-x64-xxx.zip**，解压后，将文件夹重新命名为 **redis**。

>  打开一个 **cmd** 窗口 切换到安装目录 **E:\Program Files\redis** 

E:\Program Files\redis> .\redis-server.exe .\redis.windows.conf

>  服务启动成功后，再打开一个 **cmd** 窗口运行客户端

E:\Program Files\redis> .\redis-cli.exe 

```bash
127.0.0.1:6379> set myid 123456
OK
127.0.0.1:6379> get myid
"123456"
```

## Linux 安装 redis

```bash
$ wget http://download.redis.io/releases/redis-4.0.11.tar.gz
$ tar xzf redis-4.0.11.tar.gz
$ cd redis-4.0.11
$ make
#启动redis服务 
$ src/redis-server

#再启动客户端
$ src/redis-cli
redis> set foo bar
OK
redis> get foo
"bar"
```

## 面试题

[Redis 命令参考]( http://doc.redisfans.com/) 

**Redis有哪些数据类型？**

- String、List、Set、SortedSet、Hash

**可以对List做哪些操作？**

- 左弹出一个元素 `lpop key`、左插入一个元素 `lpush key value`、阻塞左弹出一个元素 `blpop key`、求集合长度 `llen key`、修改元素 `lset key index value`、查询元素  `lindex key index`、查询所有元素 `lrange key 0 -1` 

**可以对Set做哪些操作？**

- 增加元素 `sadd key value`、随机弹出一个元素 `spop key` 、求集合长度`scard key`、查询所有元素`smembers key`
- 求交集 `sinter key1 key2`、求差集 `sdiff key1 key2`、 求并集 `sunion key1 key2`

**可以对Hash做哪些操作？**

- 增加元素 `hset key field`、 删除元素`hdel key field` 、查询元素 `hget key field`、 获取hash表中所有key  `hkeys key` 、获取hash表中所有值`hvals key` 、获取长度 `hlen  key `

**Redis除了存数据还可以干啥？**

- 发布和订阅。发布:`publish channel message`,订阅:`subscribe channel`,退订: `unsubscribe channel`,查看订阅CH1频道的个数:`pubsub numsub CH1`,查看活动的频道:`pubsub channels`
- 队列。
- 主从分区。
- 序列化支持。
- Lua脚本支持。

**Redis为什么要集群？**

- 第一种情况：数据量较大，单机无法存储大量数据，需要扩展容量。Redis是一个内存数据库，数据存储容量要受到主机内存的限制，一般主机内存在几十G，当存储的数据量超过单机内存时就需要通过集群来扩展容量。
- 第二种情况：需要一个稳定可靠的系统，允许单点故障。

**Redis怎么备份和恢复数据？**

- 在redis-cli中直接运行 `save` 命令可生成备份文件，备份文件的名称是dump.rdb 
- 将dump.rdb 移动到redis安装目录并启动redis服务即可恢复数据

**【侃大山环节】谈谈你对Redis的理解？**

Redis本质上是一个Key-Value类型的内存数据库，很像memcached