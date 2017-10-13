#!/usr/bin/env python
# -*- coding:utf-8 -*-

import os,gzip,requests,re

urls=['http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz',
'http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz']


def download(url):
  DATA_DIR='./data/'
  if(not os.path.exists(DATA_DIR)):
    os.mkdir(DATA_DIR)
  filename=DATA_DIR+re.sub('.*/(?=[^/]+)','',url)
  print 
  if(not os.path.exists(filename)):
    print 'not exists'

download(urls[0])
    

