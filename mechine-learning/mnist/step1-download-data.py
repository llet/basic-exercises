#!/usr/bin/env python
# -*- coding:utf-8 -*-

'''
日期：2017年10月13号
写一个脚本，下载mnist数据集到./data目录并提取压缩文件
模块名		功能
os		操作本地文件
re		操作字符串
gzip		提取压缩文件
requests	下载文件
'''

import os,gzip,requests,re

urls=['http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz']




def download(url):
  DATA_DIR='./data/'

  if(not os.path.exists(DATA_DIR)):
    print '创建目录: ./data'
    os.mkdir(DATA_DIR)
  filename=DATA_DIR+re.sub('.*/(?=[^/]+)','',url)

  if(not os.path.exists(filename)):
    print '开始下载文件: %s'%filename
    buf=requests.get(url) #help(requests): r = requests.get('https://www.python.org')
    gzfile=open(filename,'wb') #help(file): 'b' to the mode for binary files
    gzfile.write(buf.content) #help(requests): r = requests.get('https://www.python.org') ;r.content;
    gzfile.close()

  if(not os.path.exists(re.sub('.gz','',filename))):
    binfile=open(re.sub('.gz','',filename),'wb')
    gzfile=gzip.open(filename,'rb')
    binfile.write(gzfile.read())
    binfile.close()
    gzfile.close()


[download(urls[i]) for i in range(len(urls))]
