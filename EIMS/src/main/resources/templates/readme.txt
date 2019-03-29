1.代码生成基于velocity模板引擎，要引入相关依赖。
2.mybatis-plus默认的vm文件在mybatis-plus-generator的jar包下的/templates/中，可自行参照模板自定义，也可参照我那几个vm文件。
3.html文件生成使用了service和controller文件的生成+自定义html模板+更改后缀名实现，是取巧的生成方式：）

使用方式（必须改）：
1.更改数据库连接
2.数据库连接的用户名和密码
3.表前缀（习惯用t_table,sys_table此类命名的用户)
4.包名定义为你自己的
5.模板路径设为空或设置为自定义模板的路径，空即采用系统的模板
6.代码中要自定义的参数都有注释，还有不满足的自定义可自行参照mybatis-plus的文档

ps：不要直接采用我的模板
