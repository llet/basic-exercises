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



