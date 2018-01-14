```
1.  计算π的值 

import numpy as np 
np.sqrt(6*(np.sum(1/np.arange(1,10000,dtype=np.float)**2)))
```
```
2.  编写一个素数生成器
    # 这里使用到了生成器、过滤器、匿名函数

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
    # 测试：
print([i for i in prime(10000)])
```
```
3.  tensorflow入门练习 矩阵的乘法

import numpy as np 
import tensorflow as tf
data = np.random.rand(1024,1024)
x = tf.placeholder(tf.float32,shape=(1024,1024))
y = tf.matmul(x,x)
with tf.Session() as sess:
    print(sess.run(y,feed_dict={x:data}))
```
```
4.  tensorflow入门练习 tf.train
    # 对样本{X:[1, 2, 3, 4],y:[0, -1, -2, -3]} 进行线性回归

import tensorflow as tf

W = tf.Variable([.3], dtype=tf.float32)
b = tf.Variable([-.3], dtype=tf.float32)
x = tf.placeholder(tf.float32)
linear_model = W*x + b
y = tf.placeholder(tf.float32)
loss = tf.reduce_sum(tf.square(linear_model - y))

optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)

x_train = [1, 2, 3, 4]
y_train = [0, -1, -2, -3]

init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)
for i in range(1000):
  sess.run(train, {x: x_train, y: y_train})

curr_W, curr_b, curr_loss = sess.run([W, b, loss], {x: x_train, y: y_train})
print("W:%s,b:%s,loss:%s"%(curr_W, curr_b, curr_loss))
sess.close()


```
```
5.  tensorflow入门练习 tf.estimator

import numpy as np
import tensorflow as tf

feature_column = tf.feature_column.numeric_column("x")
estimator = tf.estimator.LinearRegressor([feature_column])

x_train = np.array([1., 2., 3., 4.])
y_train = np.array([1., 2., 3., 4.])
x_eval = np.array([2., 5., 8., 1.])
y_eval = np.array([2, 5, 8, 1.])
    # 训练模型
input_fn = tf.estimator.inputs.numpy_input_fn({"x": x_train}, y_train, batch_size=4, num_epochs=1000, shuffle=True)
estimator.train(input_fn)

train_input_fn = tf.estimator.inputs.numpy_input_fn({"x": x_train}, y_train, batch_size=4,shuffle=False)
eval_input_fn = tf.estimator.inputs.numpy_input_fn({"x": x_eval}, y_eval, batch_size=4, shuffle=False)

train_metrics = estimator.evaluate(train_input_fn)
eval_metrics = estimator.evaluate(eval_input_fn)
print("使用训练集对模型评估: %r"% train_metrics)
print("使用测试集对模型评估: %r"% eval_metrics)

```
```
6.  tensorflow入门练习 custom model tf.estimator 
import numpy as np
import tensorflow as tf

def model_fn(features, labels, mode):
# Build a linear model and predict values
  W = tf.get_variable("W", [1], dtype=tf.float64)
  b = tf.get_variable("b", [1], dtype=tf.float64)
  y = W*features['x'] + b
# Loss sub-graph
  loss = tf.reduce_sum(tf.square(y - labels))
# Training sub-graph
  global_step = tf.train.get_global_step()
  optimizer = tf.train.GradientDescentOptimizer(0.01)
  train = tf.group(optimizer.minimize(loss),tf.assign_add(global_step, 1))
# EstimatorSpec connects subgraphs we built to the
  return tf.estimator.EstimatorSpec(
      mode=mode,
      predictions=y,
      loss=loss,
      train_op=train)

estimator = tf.estimator.Estimator(model_fn=model_fn)

x_train = np.array([1., 2., 3., 4.])
y_train = np.array([0., -1., -2., -3.])
x_eval = np.array([2., 5., 8., 1.])
y_eval = np.array([-1.01, -4.1, -7., 0.])
input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_train}, y_train, batch_size=4, num_epochs=None, shuffle=True)
train_input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_train}, y_train, batch_size=4, num_epochs=1000, shuffle=False)
eval_input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_eval}, y_eval, batch_size=4, num_epochs=1000, shuffle=False)

# train
estimator.train(input_fn=input_fn, steps=1000)
# Here we evaluate how well our model did.
train_metrics = estimator.evaluate(input_fn=train_input_fn)
eval_metrics = estimator.evaluate(input_fn=eval_input_fn)
print("train metrics: %r"% train_metrics)
print("eval metrics: %r"% eval_metrics)


```
```
7.  tensorflow入门练习 mnist
import input_data
import tensorflow as tf

mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)
sess = tf.InteractiveSession()

x = tf.placeholder("float", shape=[None, 784])
y_ = tf.placeholder("float", shape=[None, 10])
W = tf.Variable(tf.zeros([784,10]))
b = tf.Variable(tf.zeros([10]))
tf.global_variables_initializer().run()

y = tf.nn.softmax(tf.matmul(x,W) + b)
cross_entropy = -tf.reduce_sum(y_*tf.log(y))
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(cross_entropy)
for i in range(1000):
  batch = mnist.train.next_batch(50)
  train_step.run(feed_dict={x: batch[0], y_: batch[1]})

correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
print(accuracy.eval(feed_dict={x: mnist.test.images, y_: mnist.test.labels}))
sess.close()
```
```
8. mnist数据处理
import numpy,gzip
import matplotlib.pyplot as plt

mnist_images=gzip.open("train-images-idx3-ubyte.gz")
buff_to_array=lambda buff,dt:numpy.frombuffer(buff,dtype=dt)
magic,num,row,col=buff_to_array(mnist_images.read(16),numpy.dtype(">i4"))
print(magic,num,row,col)
data=buff_to_array(mnist_images.read(num*row*col),numpy.dtype("uint8"))
images=numpy.reshape(data,(num,row,col))
  #打印第一张图片对应的数组
print(images[2])
plt.imshow(images[2],cmap="gray")
  #显示第一张图片
plt.show()

mnist_labels=gzip.open("train-labels-idx1-ubyte.gz")
magic2,num2=buff_to_array(mnist_labels.read(8),numpy.dtype(">i4"))
print(magic2,num)
labels=buff_to_array(mnist_labels.read(num2),numpy.dtype("uint8"))
labels[2]

```
```
9. mnist线性模型处理


