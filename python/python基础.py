#!/usr/bin/env python

# 切片
# 1
s='Hello darkness, my old friend, I\'ve come to talk with you again ' 
s=s[:-10:2]

# list生成
# 1
[x+y for x in 'ABC' for y in 'XYZ'] 
# 2
[x+'='+y for x,y in list({'X': 'A', 'Y': 'B', 'Z': 'C' }.items())] 

# generator
# 1
g = (x * x for x in range(10))
# 2
def f(i): 
  yield [1]
  x=[1,1]
  while len(x)<i:
    yield x
    x = [x[i]+x[i+1] for i in range(len(x)-1)]
    x.insert(0,1)
    x.appnd(1)

# Iterable&Iterator
# 1
from collections import Iterable,Iterator 
isinstance((),Iterable) # True
isinstance((),Iterator) # False 
isinstance([],Iterator) # False
isinstance(iter([]), Iterator) # True
# 2
x=iter([1,2,3,4]) 
while True:
  try:
    print(next(x))
  except StopIteration:
    break

# map&reduce
# 1
def f(x): 
  return x*x
list(map(f,[1,2,3,4]))
# 2
from functools import reduce 
def f(x,y):
  return x*y
reduce(f,[3,4,5,6,7])
# 3
def f(i): 
  return reduce(lambda x,y:x+y**(-3),range(i+1))

# 匿名函数
# 1
list(map(lambda x: x*x ,range(20)))
# 2
list(filter(lambda x:x%2==0,range(20)))
# 3
def f(x, y):
  return lambda: x * x + y * y

  

  
