# 排课管理系统
一个排课管理、统计系统，可批量排课、导出课表。

[SpringBoot2+Vue2版](./v1-README.md)

## UI展示
### 排课
![](./doc/img/week-view.png)

![](./doc/img/month-view.png)

![](./doc/img/batch-scheduling-course.png)

![](./doc/img/drag-drop-edit.gif)
### 导出
![](./doc/img/export.png)

![](./doc/img/excel.png)
### 报表统计
![](./doc/img/report.png)

## 技术栈
### 前端
- vue 3.5
- vue-router 4.6
- pinia 3.0
- axios 1.13
- element-plus 2.13
- fullcalendar 6.1
- echarts 6.0

### 后端
- SpringBoot 3.0
- MyBatis-Plus 3.5
- SpringDoc-OpenAPI 2.1
- POI 5.2

## 运行
### 环境
- MySQL 8.0
- JDK 17
- Maven 3.5
- Node 12.14
- Yarn 1.21
### 前端
```bash
cd tams-web
yarn
yarn serve
```
### 后端
- 新建tams数据库，执行数据库脚本 db/init_ddl.sql db/init_dml.sql
- 修改数据库信息为自己的配置 `tams-server/src/main/resources/application-dev.yaml`
- 运行 `TamsApplication`

### 访问
- 系统地址 [http://localhost:12011](http://localhost:12011)
- 接口文档地址 [http://localhost:12010/swagger-ui/index.html](http://localhost:12010/swagger-ui/index.html)

## 部署
### 环境
- docker 20.10
- docker-compose 1.26
### 映射目录
- /home/tams
  - logs 后端服务日志
  - mysql 数据库
  - nginx 前端服务器
### 访问地址
- 系统地址 [http://localhost:12011](http://localhost:12011)
### 初始安装
```bash
cd tams-server
# 1.执行打包脚本（本地）
bash package_install.sh

# 2.上传包，将tams.zip上传到要部署的服务器（本地->服务器）

# 3.执行安装脚本（服务器）
unzip tams.zip && cd tams_package && bash install.sh

# 如果安装过程中出错，可以先卸载，再重新安装
bash uninstall.sh && bash install.sh
```

> 升级
- 手动升级
```bash
cd tams-server
# 1.执行打包脚本（本地）
bash package_upgrade.sh
# 前后端同时升级 bash package_upgrade.sh  
# 后端升级 bash package_upgrade.sh 1
# 前端升级 bash package_upgrade.sh 2

# 2.上传包，将tams.zip拷贝到要部署的服务器（本地->服务器）

# 3.执行升级脚本（服务器）
unzip tams.zip && cd tams_package && bash upgrade.sh
```
- 自动升级
```bash
cd tams-server
bash auto_upgrade.sh
```
1. 首次使用自动升级需要配置免密登录

- 1.1 本地计算机生成SSH密钥对
```bash
ssh-keygen -t rsa
```
```bash
# 按照提示设置
$ Enter file in which to save the key (~/.ssh/id_rsa): (输入秘钥文件名，默认为id_rsa)
$ Enter passphrase (empty for no passphrase): (直接回车)
$ Enter same passphrase again: (直接回车)
```
- 1.2 拷贝公钥(id_rsa_tams.pub)到远程服务器
```bash
# user和server修改为远程服务器的地址和用户
ssh-copy-id -i ~/.ssh/id_rsa_tams.pub user@server
```
- 1.3 修改`auto_upgrade.sh`脚本配置
```bash
# 服务器用户
user="root"
# 服务器地址
server="x.x.x.x"
```
2. 服务器还需要安装`unzip`软件
```bash
yum install -y unzip
```

## Q&A
> [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.10.1:compile (default-compile) on project tams-server: Fatal error compiling: 无效的目标发行> [Help 1]
- 可能是Java版本不一致造成
  - 执行`mvn -v`查看Java版本是否为Java17，切换Maven关联的Java版本为17
  - 切换Java版本可以看下文的`参考》MacOS切换Java版本`

> 配置免密登录失败
1. 本地是否在`~/.ssh/config`配置了主机别名，需要在`~/.ssh/config`中添加主机配置
2. 检查服务器文件权限是否配置正确
- ~/.ssh 需要`drwx------`权限
  - chmod 700 ~/.ssh
- ~/.ssh/authorized_keys 需要`-rw-------`权限
  - chmod 600 ~/.ssh/authorized_keys

## 参考
> 关于SpringBoot3.0版本迁移
- [SpringBoot3.0迁移官方指南](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)

> 升级SpringBoot3.0后，启动报错`javax.servlet.http.HttpServletRequest not present`
- [SpringFox迁移至SpringDoc](https://springdoc.org/v2/#migrating-from-springfox)

> MacOS切换Java版本

推荐使用Java环境管理器Jenv https://github.com/jenv/jenv
1. 安装
```bash
# 安装
brew install jenv

# 配置环境变量
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
echo 'eval "$(jenv init -)"' >> ~/.zshrc
source ~/.zshrc
```
2. 使用
```bash
# 先查看java的安装目录
/usr/libexec/java_home -V

# 添加jdk
jenv add [java_home]

# 查看已安装版本
jenv versions

# 设置全局jdk版本
jenv global 17

# 查看当前jdk版本
java -version

# 如果没有设置成功，执行下面命令
source ~/.zshrc
```

