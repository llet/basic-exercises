redis 官网 ：https://redis.io/

redis http://try.redis.io/

Linux 版本: https://redis.io/download

Window 版本: https://github.com/MicrosoftArchive/redis/releases

## Window 安装 redis

---

从github上下载**Redis-x64-xxx.zip**，解压后，将文件夹重新命名为 **redis**。

### 验证

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

### 练习

http://try.redis.io/

```bash
> tutorial
SET GET
> set server:name "hello"
OK
> get server:name
"hello"
> next
 SET INCR DEL 
> set i 10
OK
> incr i
(integer) 11
> incr i
(integer) 12
> del i
(integer) 1
```

```
decr , decrby, del, exists, expire, get, getset, hdel, hexists, hget, hgetall, hincrby, hkeys, hlen, hmget, hmset, hset, hvals, incr, incrby, keys, lindex, llen, lpop, lpush, lrange, lrem, lset, ltrim, mget,mset, msetnx, multi, pexpire, rename, renamenx, rpop, rpoplpush, rpush, sadd, scard, sdiff, sdiffstore, set, setex, setnx, sinter, sinterstore, sismember, smembers, smove, sort, spop, srandmember, srem, sunion, sunionstore, ttl, type, zadd, zcard, zcount, zincrby, zrange, zrangebyscore, zrank, zrem, zremrangebyscore, zrevrange, zscore
```



integer string hash list set 

set i 10 ，设置i=10。get i ，获取i的值。

**incr i** 自增1。**decr i** 自减1。**del i** 删除一个键，删除成功返回1，键不存在返回0。

**decrby i 2**  以2递减。**exists i** ：i存在则返回1。expire i 10 ，设置有效时间为10秒。

getset i 123 ，原子操作，先获取原值，再重新赋值。



### list

序号	命令及描述

> 1	BLPOP key1 [key2 ] timeout 

移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。

> 2	BRPOP key1 [key2 ] timeout  

移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。

> 3	BRPOPLPUSH source destination timeout 

从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
4	LINDEX key index 
通过索引获取列表中的元素
5	LINSERT key BEFORE|AFTER pivot value 
在列表的元素前或者后插入元素
6	LLEN key 
获取列表长度
7	LPOP key 
移出并获取列表的第一个元素
8	LPUSH key value1 [value2] 
将一个或多个值插入到列表头部
9	LPUSHX key value 
将一个值插入到已存在的列表头部
10	LRANGE key start stop 
获取列表指定范围内的元素
11	LREM key count value 
移除列表元素
12	LSET key index value 
通过索引设置列表元素的值
13	LTRIM key start stop 
对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
14	RPOP key 
移除并获取列表最后一个元素
15	RPOPLPUSH source destination 
移除列表的最后一个元素，并将该元素添加到另一个列表并返回
16	RPUSH key value1 [value2] 
在列表中添加一个或多个值
17	RPUSHX key value 
为已存在的列表添加值