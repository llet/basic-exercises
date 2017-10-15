#!/usr/bin/env python
# -*- coding:utf-8 -*- 
import os
import tensorflow as tf
import numpy as np
os.environ['TF_CPP_MIN_LOG_LEVEL']='4'

def model_fn(features, labels, mode):
  # Build a linear model and predict values
  k = tf.get_variable("k", [1], dtype=tf.float64)
  b = tf.get_variable("b", [1], dtype=tf.float64)
  y_model = k * features['x'] + b
  # Loss sub-graph
  loss = tf.reduce_sum(tf.square(y_model - labels))
  # Training sub-graph
  global_step = tf.train.get_global_step()
  optimizer = tf.train.GradientDescentOptimizer(0.01)
  train = tf.group(optimizer.minimize(loss),tf.assign_add(global_step, 1))
  # EstimatorSpec connects subgraphs we built to the appropriate functionality.
  return tf.estimator.EstimatorSpec(mode=mode, predictions=y_model,loss=loss,train_op=train)

estimator = tf.estimator.Estimator(model_fn=model_fn)
# define our data sets
x = np.array([1., 2., 3., 4.])
y = np.array([0., -1., -2., -3.])
x_eval = np.array([2., 5., 8., 1.])
y_eval = np.array([-1.01, -4.1, -7, 0.])
input_fn = tf.estimator.inputs.numpy_input_fn({"x": x}, y, batch_size=4, num_epochs=None, shuffle=True)
train_input_fn = tf.estimator.inputs.numpy_input_fn({"x": x}, y, batch_size=4, num_epochs=1000, shuffle=False)
eval_input_fn = tf.estimator.inputs.numpy_input_fn({"x": x_eval}, y_eval, batch_size=4, num_epochs=1000, shuffle=False)

# train
estimator.train(input_fn=input_fn, steps=1000)
# Here we evaluate how well our model did.
train_metrics = estimator.evaluate(input_fn=train_input_fn)
eval_metrics = estimator.evaluate(input_fn=eval_input_fn)
print "train metrics: %r"% train_metrics
print "eval metrics: %r"% eval_metrics

