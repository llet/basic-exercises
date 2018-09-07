### 基本概念

---

- **工作区：**就是你在电脑里能看到的目录。

- **暂存区：**".git目录下" 下的index文件

- **版本库：**工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。

- **对象库：**位于 ".git/objects" 目录下，里面包含了创建的各种对象及内容。

- **HEAD：** 指向某分支的一个"游标"

- `HEAD`: The current ref that you’re looking at. In most cases it’s probably refs/heads/master 

  当前目录下最近的一次commit
- `FETCH_HEAD`: The SHAs of branch/remote heads that were updated during the last git fetch  从远端或分支fetch过来的标志
- `ORIG_HEAD`: When doing a merge, this is the SHA of the branch you’re merging into. 
做合并时合并后的分支
- `MERGE_HEAD`: When doing a merge, this is the SHA of the branch you’re merging from. 
合并时合并前的分支
- `CHERRY_PICK_HEAD`: When doing a cherry-pick, this is the SHA of the commit which you are cherry-picking.

![](./img/git/git-01.jpg)

1.  "git add"时 ，暂存区的目录树被更新，同时工作区修改（或新增）的文件内容被写入到对象库中的一个新的对象中，而该对象的ID被记录在index中。
2. "git commit"时，暂存区的目录树写到版本库（对象库）中，master 分支会做相应的更新。即 master 指向的目录树就是提交时暂存区的目录树。
3.  "git checkout ." 或者 "git checkout --filename" 时，会用暂存区全部或指定的文件覆盖工作区的文件。这个操作很危险，会清除工作区中未添加到暂存区的改动。
4. "git reset HEAD" 时，暂存区的目录树会被重写，被 master 分支指向的目录树所覆盖，但是工作区不受影响。
5.  "git rm --cached filename" 时，会直接从暂存区删除文件，工作区则不做出改变。
6.  "git checkout HEAD ." 或者 "git checkout HEAD filename" 命令时，会用 HEAD 指向的 master 分支中的全部或者部分文件覆盖暂存区和以及工作区中的文件。这个命令也是极具危险性的，因为不但会清除工作区中未提交的改动，也会清除暂存区中未提交的改动。


### 常用命令

---

```bash
#查看分支
git branch 
git branch -a
#创建分支
git branch branchname
#切换分支
git checkout branchname
#合并分支
git merge 
#从远程仓库下载新分支与数据
git fetch
#从远端仓库提取数据并尝试合并到当前分支：
git merge
#要查看当前配置有哪些远程仓库，可以用命令：
git remote
git remote -v
git show # 显示某次提交的内容 git show $id
git add <file> # 将工作文件修改提交到本地暂存区
git rm <file> # 从版本库中删除文件
git reset <file> # 从暂存区恢复到工作文件
git reset HEAD # 恢复最近一次提交过的状态，即放弃上次提交后的所有本次修改
git diff <file> # 比较当前文件和暂存区文件差异 git diff
git log -p <file> # 查看每次详细修改内容的diff
git branch -r # 查看远程分支
git merge <branch> # 将branch分支合并到当前分支
git stash # 暂存
git stash pop #恢复最近一次的暂存
git pull # 抓取远程仓库所有分支更新并合并到本地
git push origin master # 将本地主分支推到远程主分支
```



### 面试题

---

**git add 和 git stage 有什么区别**

没区别，都是将工作区的文件提交到暂存区。

**git reset、git revert 和 git checkout 有什么区别**

 git reset 是直接删除暂存区到版本库的 commit 记录

**fetch和merge和pull的区别**

git fetch：相当于是从远程获取最新版本到本地，不会自动merge，git merge :  将内容合并到当前分支，git pull：相当于是从远程获取最新版本并merge到本地

**tag**

tag指向一次commitId，通常用来给开发分支做一个标记

**Git和SVN的区别**

Git是分布式版本控制系统，SVN是集中式版本控制系统

**Git工作流程**

**平时都用什么 git 工具？**

目前用的是 gitGUI

**.gitignore忽略规则简单说明**
```
#               表示此为注释,将被Git忽略
*.a             表示忽略所有 .a 结尾的文件
!lib.a          表示但lib.a除外
/TODO           表示仅仅忽略项目根目录下的 TODO 文件，不包括 subdir/TODO
build/          表示忽略 build/目录下的所有文件，过滤整个build文件夹；
doc/*.txt       表示会忽略doc/notes.txt但不包括 doc/server/arch.txt
 
bin/:           表示忽略当前路径下的bin文件夹，该文件夹下的所有内容都会被忽略，不忽略 bin 文件
/bin:           表示忽略根目录下的bin文件
/*.c:           表示忽略cat.c，不忽略 build/cat.c
debug/*.obj:    表示忽略debug/io.obj，不忽略 debug/common/io.obj和tools/debug/io.obj
**/foo:         表示忽略/foo,a/foo,a/b/foo等
a/**/b:         表示忽略a/b, a/x/b,a/x/y/b等
!/bin/run.sh    表示不忽略bin目录下的run.sh文件
*.log:          表示忽略所有 .log 文件
config.php:     表示忽略当前路径的 config.php 文件
 
/mtk/           表示过滤整个文件夹
*.zip           表示过滤所有.zip文件
/mtk/do.c       表示过滤某个具体文件
 
被过滤掉的文件就不会出现在git仓库中（gitlab或github）了，当然本地库中还有，只是push的时候不会上传。
 
需要注意的是，gitignore还可以指定要将哪些文件添加到版本管理中，如下：
!*.zip
!/mtk/one.txt
 
唯一的区别就是规则开头多了一个感叹号，Git会将满足这类规则的文件添加到版本管理中。为什么要有两种规则呢？
想象一个场景：假如我们只需要管理/mtk/目录中的one.txt文件，这个目录中的其他文件都不需要管理，那么.gitignore规则应写为：：
/mtk/*
!/mtk/one.txt
 
假设我们只有过滤规则，而没有添加规则，那么我们就需要把/mtk/目录下除了one.txt以外的所有文件都写出来！
注意上面的/mtk/*不能写为/mtk/，否则父目录被前面的规则排除掉了，one.txt文件虽然加了!过滤规则，也不会生效！
 
----------------------------------------------------------------------------------
还有一些规则如下：
fd1/*
说明：忽略目录 fd1 下的全部内容；注意，不管是根目录下的 /fd1/ 目录，还是某个子目录 /child/fd1/ 目录，都会被忽略；
 
/fd1/*
说明：忽略根目录下的 /fd1/ 目录的全部内容；
 
/*
!.gitignore
!/fw/ 
/fw/*
!/fw/bin/
!/fw/sf/
说明：忽略全部内容，但是不忽略 .gitignore 文件、根目录下的 /fw/bin/ 和 /fw/sf/ 目录；注意要先对bin/的父目录使用!规则，使其不被排除。
```
**开源软件和商业软件版本的介绍**
```
开发阶段划分：Alpha版：内测版，内部交流或者专业测试人员测试用。
Beta版：公测版，专业爱好者大规模测试用
Gamma版：成熟的测试版
RC版：Release Candidate
RC版：Release Candidate　的缩写，意思是发布倒计时，候选版本，处于Gamma阶段，该版本已经完成全部功能并清除大部分的BUG。
Final：正式版
GA：general availability 首次发行稳定的版本
其他版本
Enhance：增强版或者加强版，属于正式版1
Free：自由版
Release：发行版，有时间限制
Upgrade：升级版
Retail：零售版
Cardware：属共享软件的一种，只要给作者回复一封电邮或明信片即可。（有的作者并由此提供注册码等），目前这种形式已不多见。/　S
Plus：属增强版，不过这种大部分是在程序界面及多媒体功能上增强。
Preview：预览版
Corporation&Enterprise：企业版
Standard：标准版
Mini：迷你版也叫精简版只有最基本的功能
Premium：贵价版
Professional：专业版
Express：特别版
Deluxe：豪华版
Regged：已注册版
```
