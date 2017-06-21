1、安装docker环境
----


2、管理节点上初始化swarm
----
```bash
docker swarm init --advertise-addr <MANAGER-IP>
```

参考资料：
https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/
https://docs.docker.com/engine/swarm/swarm-tutorial/add-nodes/

3、按照提示创建manager或worker节点
----