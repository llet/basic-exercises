#!/usr/bin/env python
# -*- coding:utf-8 -*- 
import os
import tensorflow as tf
import numpy as np
os.environ['TF_CPP_MIN_LOG_LEVEL']='2'

data = [[1,2,3,4],[0,-1,-2,-3]]
#data = [[2,3,4,5,6,7],[1,2,3,4,5,6]]
k = tf.Variable(1.2)
b = tf.Variable(-1.3)
x = tf.placeholder(tf.float32)
y_model = k*x + b

init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)
print 'x = ',data[0]
print 'y = ',data[1]
print 'y_model = k*x+b;k = 0.1;b = 0.2'
print 'y_model =',sess.run(y_model,{x:data[0]})
y = tf.placeholder(tf.float32)
loss = tf.reduce_sum(tf.square(y-y_model))
print 'loss = sum([(k*x[i]+b-y[i])**2 for i in range(len(x))]) =',sess.run(loss,{x:data[0],y:data[1]})


#对于data = [[2,3,4,5,6,7],[1,2,3,4,5,6]]，0.01的学习率就不使用了，需要改成0.0001
optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)
for i in range(1000):
  sess.run(train,{x:data[0],y:data[1]})
  if i%100 == 0:
    print 'loss =',sess.run(loss,{x:data[0],y:data[1]}),'; [k,b] =',sess.run([k, b])

