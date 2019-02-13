1.由于使用mybatis-plus,一般的数据库操作已经不再需要在Mapper文件中填写，但复杂的操作仍然可以使用普通的mybatis的写法，故保留此文件当作参考。
2.在不需要验证sql语句的空值问题时，个人建议直接使用@query简化开发，当然请根据实际开发情况或个人爱好判断

1.because of used mybatis-plus develop my project,common dao sql is need not to write to mapper file,but you still can use mybatis style write your mapper file
2.i recommend use the @query direct write your sql,especially need not validate sql,but all follow your heart