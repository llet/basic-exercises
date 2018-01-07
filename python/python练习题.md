```
1.  计算π的值 
import numpy as np 
np.sqrt(6*(np.sum(1/np.arange(1,10000,dtype=np.float)**2)))
```
```
2.  编写一个素数生成器
    这里使用到了生成器、过滤器、匿名函数
def prime(max=100):
    yield 2
    gen=(i for i in range(3,max) if i%2==1)
    while True:
        try:
            n=next(gen)
            yield n
            gen=filter((lambda n:lambda x:x%n>0)(n),gen)
        except StopIteration:
            break
    测试：
print([i for i in prime(10000)])
```

