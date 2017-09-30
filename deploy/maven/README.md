拉取代码（第一次）
======

```bash    
cd /usr/local/eshop/src
git clone http://218.17.157.53:18089/eshop/server.git
```

更新代码
======

```bash    
cd /usr/local/eshop/src/server
git checkout xxx
git pull
```

构建docker镜像
======
```bash    
sudo mvn clean package -Dmaven.test.skip -DpushImage
```