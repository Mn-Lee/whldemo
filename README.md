## 白鹿巷预网站

## 资料、文档
[Spring文档](https://spring.io/guides) \
[Spring-Web(SoringMVC)](https://spring.io/guides/gs/serving-web-content) \
[BootStrap](https://v3.bootcss.com/) \
[OKHttp3](https://square.github.io/okhttp/) \
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) \
[maven repository](https://mvnrepository.com/) \

## 工具
jdk10 \
idea \
git \
maven \
spring \
springMVC \
bootStrap \
okhttp \
fastjson \

## 脚本
```sql
create table user
(
  id           int auto_increment 
                            primary key,
  account_id   varchar(100) null,
  name         varchar(50)  null,
  token        char(36)     null,
  gmt_create   bigint       null,
  gmt_modified bigint       null
);
```