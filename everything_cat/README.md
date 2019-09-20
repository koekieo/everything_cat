##Everything

###1.简介

仿照Everything桌面工具，基于Java语言开发的命令行文件搜索工具 

###2.背景 

有时候在windows命令行下需要查询一些文件，由于for命令并不如Linux下的find命令好用，所以DIY开发一款命令 行工具，用来实现Windows命令行中搜索文件。

###3.意义 

解决Windows命令行下文件搜索问题 

基于Java开发的工具可以在Windows和Linux平台上无差异使用 

锻炼编码能力 

###4.技术

Java（文件操作） 

Database（嵌入式H2数据库或者MySQL数据库） 

JDBC 

Lombok库（IDEA安装Lombok插件） 

Java的多线程 

文件系统监控（Apache Commons IO）

###5.用法

#### 6.1 使用
+ java -jar everything_cat-1.0.0-cmd.jar args

+ args:
+ --rebuildIndex=true/false : true表示重建索引,false表示不重建
+ --maxReturnFile=30 : 最大返回的文件数量
+ --indexPaths=file path : 索引的文件路径
+ --excludePaths=file path : 排除的文件路径
+ --depthAsc=true/false : true安装文件层级深度升序,false安装文件层级深度降序

#### 6.2命令


\>>help\
命令列表:\
退出: quit\
帮助: help\
索引：index\
搜索：search <name> [<file-Type> img | doc | bin | archive | other]

####6.3搜索
+ 欢迎使用，Everything
