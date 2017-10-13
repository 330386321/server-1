安装fis3
======
设置全局
-----
把 $(cnpm prefix -g)/bin 目录设置到 PATH 中
```bash
sudo echo -e "export PATH=$(cnpm prefix -g)/bin:$PATH" >> ~/.bashrc && source ~/.bashrc
```

安装
------
```bash
cnpm install -g fis3
```

查看版本
------
```bash
fis3 -v
```

打包
------
打开到html项目的根目录
```bash
rm -fr dist
fis3 release -d dist
```