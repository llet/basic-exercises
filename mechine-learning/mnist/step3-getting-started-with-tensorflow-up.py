#!/usr/bin/env python
# -*- coding:utf-8 -*- 
import os
import tensorflow as tf
import numpy as np
os.environ['TF_CPP_MIN_LOG_LEVEL']='4'

data = [[1., 2., 3., 4.,5.],[0., -1., -2., -3.,-4]]
data2 = [[2., 5., 8., 1.,9],[-1.01, -4.1, -7, 0.,-8]]
def model_fn(features , labels , mode):
  k = tf.get_variable("k", [1], dtype=tf.float64)
  b = tf.get_variable("b", [1], dtype=tf.float64)
  y_model = k * features['x'] + b
  loss = tf.reduce_sum(tf.square(y_model - labels))
  # Training sub-graph
  global_step = tf.train.get_global_step()
  optimizer = tf.train.GradientDescentOptimizer(0.01)
  train = tf.group(optimizer.minimize(loss),tf.assign_add(global_step, 1))
  # EstimatorSpec connects subgraphs we built to the appropriate functionality.
  return tf.estimator.EstimatorSpec(mode=mode, predictions=y_model,loss=loss,train_op=train)

estimator = tf.estimator.Estimator(model_fn=model_fn)
# define our data sets
# batch_size We have to tell the function how many batches 有多少批次
# num_epochs how big each batch should be.每批次有多少数据
# shuffle 重新排列
input_fn = tf.estimator.inputs.numpy_input_fn({"x": np.array(data[0])},np.array(data[1]), batch_size=6, num_epochs=None, shuffle=True)
input_fn2 = tf.estimator.inputs.numpy_input_fn({"x": np.array(data[0])}, np.array(data[1]), batch_size=6, num_epochs=1000, shuffle=False)
input_fn3 = tf.estimator.inputs.numpy_input_fn({"x": np.array(data2[0])}, np.array(data2[1]), batch_size=6, num_epochs=1000, shuffle=False)

# train 
# steps 步骤数
estimator.train(input_fn=input_fn, steps=1000)
# Here we evaluate how well our model did.
metrics2 = estimator.evaluate(input_fn=input_fn2)
metrics3 = estimator.evaluate(input_fn=input_fn3)
print "metrics2: ", metrics2
print "metrics3: ", metrics3

