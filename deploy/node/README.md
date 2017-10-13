安装node
======
下载
-----
```bash
cd /usr/local/eshop/
mkdir node
cd node
sudo wget http://cdn.npm.taobao.org/dist/node/v8.6.0/node-v8.6.0-linux-x64.tar.xz
```

解压
-----
```bash
sudo tar -xJf node-v8.6.0-linux-x64.tar.xz
```

设置全局命令
-----
```bash
sudo ln -s /usr/local/eshop/node/node-v8.6.0-linux-x64/bin/npm  /usr/local/bin/
sudo ln -s /usr/local/eshop/node/node-v8.6.0-linux-x64/bin/node  /usr/local/bin/
```

安装cnpm
------
建议使用淘宝镜像安装，否则会很慢的
```bash
npm install -g cnpm --registry=https://registry.npm.taobao.org
sudo ln -s /usr/local/eshop/node/node-v8.6.0-linux-x64/bin/cnpm /usr/local/bin/
```