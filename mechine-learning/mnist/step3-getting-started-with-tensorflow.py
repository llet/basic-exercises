#!/usr/bin/env python
# -*- coding:utf-8 -*- 
import os
import tensorflow as tf
os.environ['TF_CPP_MIN_LOG_LEVEL']='2'


k = tf.Variable([0.1])
b = tf.Variable([0.2])
x = tf.placeholder(tf.float32)
y_model = k*x+b

init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)
print 'x = [2,3,4,5,6,7]'
print 'y = [1,2,3,4,5,6]'
print 'y_model = k*x+b;k = 0.1;b = 0.2'
print 'y_model =',sess.run(y_model,{x:[2,3,4,5,6,7]})
y=tf.placeholder(tf.float32)
loss=tf.reduce_sum(tf.square(y_model-y))
print 'loss = sum([(k*x[i]+b-y[i])**2 for i in range(len(x))]) =',sess.run(loss,{x:[2,3,4,5,6,7],y:[1,2,3,4,5,6]})



optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)
for i in range(1000):
  sess.run(train,{x:[2,3,4,5,6,7],y:[1,2,3,4,5,6]})
print(sess.run([k, b]))

