# Elastic-Job-Console

# [Elastic-Job-Lite中文主页](http://dangdangdotcom.github.io/elastic-job/elastic-job-lite)
# [Elastic-Job-Cloud中文主页](http://dangdangdotcom.github.io/elastic-job/elastic-job-cloud)

## 安装部署
解压缩elastic-job-lite-console-${version}.tar.gz并执行bin\start.sh。打开浏览器访问http://localhost:8899/即可访问控制台。8899为默认端口号，可通过启动脚本输入-p自定义端口号。

elastic-job-lite-console-${version}.tar.gz可通过mvn install编译获取。

在${user_home}创建.elastic-job-console文件夹，把conf中的Configurations.xml文件复制进去

## 登录
提供两种账户，管理员及访客，管理员拥有全部操作权限，访客仅拥有察看权限。默认管理员用户名和密码是root/root，访客用户名和密码是guest/guest，可通过conf\auth.properties修改管理员及访客用户名及密码。
