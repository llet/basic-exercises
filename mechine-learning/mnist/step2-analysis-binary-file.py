#!/usr/bin/env python
# -*- coding:utf-8 -*- 


'''
这一步主要是分析下下面的两个二进制文件，看看这里面是怎么保存标签和图片的
参考: http://www.cnblogs.com/x1957/archive/2012/06/02/2531503.html
模块		功能
struct		对二进制数据打包和解包
matplotlib	显示图片
'''
image='./data/train-images-idx3-ubyte'
label='./data/train-labels-idx1-ubyte'

import struct 
import matplotlib.pyplot as plt
import numpy as np 

image=open(image,'rb')
ibuf=image.read()
print '大端规则,先看看前4个字节: ',struct.unpack_from('4B',ibuf,0)
print '前四个字节表示: ',struct.unpack_from('>i',ibuf,0)[0],'这个数字被称为magic number'
print struct.unpack_from('4B',struct.pack('i',50855936),0),'pack("i",50855936)'
print struct.unpack_from('4B',struct.pack('i',2051),0),'pack("i",2051)'
print struct.unpack_from('4B',struct.pack('>i',50855936),0),'pack(">i",50855936)'
print struct.unpack_from('4B',struct.pack('>i',2051),0),'pack(">i",2051)'
print '这就比较坑了,对于int默认使用小端规则进行pack和unpack'

print 'integer1: ',struct.unpack_from('>i',ibuf,0)
print 'integer2: ',struct.unpack_from('>i',ibuf,4)
print 'integer3: ',struct.unpack_from('>i',ibuf,8)
print 'integer4: ',struct.unpack_from('>i',ibuf,12)



#再看看图片
im=struct.unpack_from('784B',ibuf,16)
plt.imshow(np.array(im).reshape(28,-1),cmap='gray')
plt.show()
