安装
======
```bash
docker-machine create \
  --driver generic \
  --generic-ip-address=192.168.1.76 \
  --generic-ssh-key ~/.ssh/id_rsa \
  vm1
```

```bash
docker-machine create \
  -d none \
  --url=tcp://192.168.1.130:2375 \
  vm2
```

```bash
docker-machine create \
  --driver generic \
  --generic-ip-address=192.168.1.130 \
  --generic-ssh-key ~/.ssh/id_rsa \
  vm2
```


https://docs.docker.com/machine/drivers/generic/#example