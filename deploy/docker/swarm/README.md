1、安装docker环境
----


2、管理节点上初始化swarm
----
```bash
sudo docker swarm init --advertise-addr <MANAGER-IP>
```

3、获取加入swarm管理节点的token
----
```bash
sudo docker swarm join-token worker
```
4、在其他服务器执行，即可加入swarm管理节点
```bash
docker swarm join \
    --token SWMTKN-1-5wtlwtip2zg8bj1f4l12rlj66i777ivsx55qs2xyi7dntknuti-d8sxx8x72aisv64neb42vslgg \
    192.168.100.73:2377
```
4、创建

参考资料：
https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/
https://docs.docker.com/engine/swarm/swarm-tutorial/add-nodes/