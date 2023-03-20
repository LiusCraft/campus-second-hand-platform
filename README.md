# 校园二手平台

#### 介绍

校园二手交易平台毕设

内置发布商品与修改商品，模拟订单。

内置管理后台，可管理所有订单和商品以及添加分类。

管理后台可管理用户的角色与昵称、邮箱。

有反馈功能与后台查看反馈信息。

#### 软件架构

后端用到了springboot、mybatis-plus、[luckpermission](https://gitee.com/liuscraft/luck-permission)(自己写的一个权限工具包)

前端用到了 vue2、vuetify2、axios、router、vuex

#### 安装教程

1.导入数据库
sql脚本文件根路径下的trading.sql

2.修改applicati配置文件，与您的MySQL连接上。

3.去我的luck-permission仓库里拉取下来，然后使用maven的install将该包放在自己的本地maven repo下
或者通过下面方式引入
```xml
<repositories>
    <repository>
        <id>liuscraft-maven</id>
        <url>https://gitee.com/liuscraft/maven/raw/master</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.liuscraft</groupId>
        <artifactId>luck-permission-spring-boot-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### 使用说明

1.前端项目需要先npm install(在根目录下的ui文件夹下),然后再执行npm run serve即可启动前端项目

2.后端启动springboot项目即可！

