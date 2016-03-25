feilong-platform feilong-servlet
================

Reduce development, Release ideas

            .--.
           /    \
          ## a  a
          (   '._)
           |'-- |
         _.\___/_   ___feilong___
       ."\> \Y/|<'.  '._.-'
      /  \ \_\/ /  '-' /
      | --'\_/|/ |   _/
      |___.-' |  |`'`
        |     |  |
        |    / './
       /__./` | |
          \   | |
           \  | |
           ;  | |
           /  | |
     jgs  |___\_.\_
          `-"--'---'


#Welcome to feilong-platform feilong-servlet

`封装了j2ee常用类,是feilong-platform web相关jar 的基础`

#说明

1. 基于`Apache2` 协议,您可以下载,代码用于闭源项目,但每个修改的过的文件必须放置版权说明;
1. 基于`maven3.2`构建;
1. 1.5.0及以上版本需要`jdk1.7`及以上环境(1.5.0以下版本需要`jdk1.6`及以上环境);


# Maven使用配置

```XML
	<project>
		....
		<repositories>
			<repository>
				<id>feilong-repository</id>
				<url>https://raw.github.com/venusdrogon/feilong-platform/repository</url>
			</repository>
		</repositories>
		
		....
		<dependencies>
			....
			<dependency>
				<groupId>com.feilong.platform</groupId>
				<artifactId>feilong-servlet</artifactId>
				<version>1.4.1</version>
			</dependency>
			....
		</dependencies>
		
		....
		
	</project>
```

# feilong-platform module:

Category |Name | Description | JDK编译版本
----|------------ | ---------|------------
commons |`feilong-servlet` | 封装了j2ee常用类,是feilong-platform web相关jar 的基础 | 1.7

# Javadoc:
在此,我们提供在线的Javadoc,以便查阅,参见 [Javadoc](http://venusdrogon.github.io/feilong-platform/javadocs/1.5.0-SNAPSHOT/feilong-servlet/)


# About

如果您对feilong platform 有任何建议，可以使用下面的联系方式：

* 新浪微博:http://weibo.com/venusdrogon
* iteye博客:http://feitianbenyue.iteye.com/