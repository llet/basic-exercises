```
1.  计算π的值 
import numpy as np 
np.sqrt(6*(np.sum(1/np.arange(1,10000,dtype=np.float)**2)))
```
```
2.  素数生成器
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

