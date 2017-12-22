#!/usr/bin/env python

'''
'''

'''
切片
'''
s = 'Hello darkness, my old friend, I\'ve come to talk with you again '
s[:-10:2]

'''
生成器 generator
'''

g = (x * x for x in range(10))

def f(i):
  yield [1]
  x=[1,1]
  while len(x)<i:
    yield x
    x = [x[i]+x[i+1] for i in range(len(x)-1)]
    x.insert(0,1)
    x.appnd(1)

'''
迭代器 Iterable
'''
from collections import Iterable

isinstance((),Iterable)

'''
匿名函数
'''
list(map(lambda x: x*x ,range(20)))

list(filter(lambda x:x%2==0,range(20)))

def f(x, y):
  return lambda: x * x + y * y