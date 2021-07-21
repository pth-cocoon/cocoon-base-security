# cocoon-base-security

## 项目描述

这是一个对SpringSecurity的一个封装。因为SpringSecurity学习成本较高，太重了。所以做了很大程度上的封装和默认实现，使其使用起来会更加简单，快捷。

## 下载安装

本项目的Jar包已上传腾讯云的Maven托管，同时也可以自行打包上传到自己的Maven私服，或者本地mvn install

```xml
    <dependency>
      <groupId>vin.pth</groupId>
      <artifactId>cocoon-base-security</artifactId>
      <version>0.0.1</version>
    </dependency>
```

使用时，maven引入即可

## 相关项目

首先项目依赖 ：

1. spring-boot-starter-web
2. spring-boot-starter-security

## 项目说明

### 这个项目做了以下事情：

1. 配置文件的封装。虽然WebSecurityConfigurerAdapter让配置文件的编写已经简便了很多，想要跑起来需要配置的内容还是太多了。所以提供了SecurityConfiguration抽象类，进行了大量的默认配置。包括默认放行Options请求，以及允许跨域等。很重要的是，封装了rbacService的bean作为权限控制的关键点。注入名为rbacService的组件，即可实现自定义的权限控制。
2. 封装了很多情况下（个人使用过程中）适用并且可扩展的LoginFilter和TokenFilter。作用分别是拦截登陆请求，处理登陆业务 和 拦截token，设置当前登陆用户。并且登陆使用了TokenFactory返回指定不同类型的AuthenticationToken。
3. 由于 TokenFactory 决定了返回的AuthenticationToken类型，配合AuthenticationToken和provider可以实现对于登陆方式的拓展。邮件验证码，手机验证码，等登陆方式。
4. 提供了多个service接口规范，只需要实现接口即可实现相应的功能。
    * RbacService: 真正决定接口访问是否通过。
    * TokenService: 针对Token的处理，可以支持Jwt，或者Redis等。自行实现即可。

### 使用

1. 继承SecurityConfiguration同时添加 @EnableWebSecurity 和 @Configuration 编写自己的登陆成功和失败的处理器。
2. 实现相关服务：
    1. RbacService（注意修改Bean名称）
    2. TokenService
    3. UserDetailsService(SpringSecurity内置接口)

至此，一个简单的权限控制已经实现。用TokenService控制当前登陆用户，RbacService控制用户是否可以访问，UserDetailsService，SpringSecurity 规定强制根据用户名检索唯一用户。遵循其规范。



## 待完成

1. Demo项目
2. 多种登陆方式相关文档
3. ......

