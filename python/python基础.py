#!/usr/bin/env python

# 切片 1
s='Hello darkness, my old friend, I\'ve come to talk with you again ' 
s=s[:-10:2]

# list生成 1
[x+y for x in 'ABC' for y in 'XYZ'] 
# list生成 2
[x+'='+y for x,y in list({'X': 'A', 'Y': 'B', 'Z': 'C' }.items())] 
	
# generator 1
g=(x * x for x in range(10))
# generator 2
def f(i): 
	yield [1]
	x=[1,1]
	while len(x)<i:
		yield x
		x = [x[i]+x[i+1] for i in range(len(x)-1)]
		x.insert(0,1)
		x.append(1)

# Iterable & Iterator 1
from collections import Iterable,Iterator 
isinstance((),Iterable) # True
isinstance((),Iterator) # False 
isinstance([],Iterator) # False
isinstance(iter([]), Iterator) # True

# Iterable & Iterator 2
x=iter([1,2,3,4]) 
while True:
	try:
		print(next(x))
	except StopIteration:
		break

# map & reduce 1
def f(x): 
	return x*x
list(map(f,[1,2,3,4]))

# map & reduce 2
from functools import reduce 
def f(x,y):
	return x*y
reduce(f,[3,4,5,6,7])

# map & reduce 3
def f(i): 
	return reduce(lambda x,y:x+y**(-3),range(i+1))

# filter 1
list(filter(lambda x:x%2=1,range(20)))

# filter 2 
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

# 匿名函数 1
list(map(lambda x: x*x ,range(20)))
# 匿名函数 2
list(filter(lambda x:x%2==0,range(20)))
# 匿名函数 3
def f(x, y):
	return lambda: x * x + y * y

# __slots__
class Student(object):
		__slots__ = ('name', 'age') # __slots__ 限制实例的属性只能包含name,age

