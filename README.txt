图书管理系统后端
功能概述

**用户端：**
图书查询：根据图书编号、图书名称查询图书信息，可查询图书的编号、名称、作者、价格、在馆数量等。
借阅历史：查询自己以往的借阅历史，包括哪些图书等具体信息、借阅日期
我的：查看个人资料，修改账户密码，退出系统。

**管理员端：**
图书管理：根据图书编号、图书名称查询图书基本信息，添加、修改、删除图书。
图书借阅：展示所有正在借阅图书的信息。
公告管理：向用户发布公告。
读者管理：根据手机号码、姓名查询读者基本信息，添加、修改、删除读者信息。
我的：查看个人资料，修改账户密码，退出系统。

# 1、环境SSM
- tomcat：10.0.27
- idea： 2022.2.3
- vs code 1.75

目录结构简单说明
library_management_system --父工程
    library_management_system_version_1.0 --1.0版本
        --httpapi 本次web项目的所有api接口,用于前端调用
        --resources 资源目录
            -db.properties 数据路配置文件
            -log4j.properties log4j配置文件
            -mybatis-config.xml mybatis配置文件
        --Java 源码目录
            --com.xxl
                --aop.Jsonresult 切面
                    -JsonResultAop 用切面监控返回的json数据
                --config 配置
                    -ApplicationConfig  把DaoConfig和SpringMvcConfig加载在一起
                    -DaoConfig  数据库相关的配置
                    -RegisterConfigClass 注册ApplicationConfig类,web.xml读取此RegisterConfigClass间接读取ApplicationConfig
                    -SpringMvcConfig  spring mvc相关的配置
                --controller 处理请求
                    -AnnouncementController  公告控制器
                    -BookController 书籍控制器
                    -BorrowBookHistoryController  借阅历史控制器
                    -UserController  用户控制器
                --dao dao层
                    --announcement
                    --book
                    --history
                    --user
                --filter 全局过滤器
                    -EncodingFilter 字符过滤,解决跨域一些问题
                --interceptor 全局拦截器
                    -LoginInterceptor  登录状态过滤器
                    -PermissionsInterceptor  权限过滤器
                --pojo 图书管理系统相关的实体类
                --service 业务层
                    --announcement
                    --book
                    --borrowhistory
                    --user
                --util 工具
                    --Jsonresult  负责向前端传递json数据
                    -ConstantUtil 记录全局的变量
                    -IoUtil  io相关的操作
                    -LogUtil log4j相关的操作
                    -TimeUtil 时间相关的操作
                    -UUIDUtil  UUid工具类
        --webappweb 项目配置目录
            --WEB-INF web目录
                --bookimg --存放上传图书文件的目录
                -web.xml web配置
