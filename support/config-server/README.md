#config-server

```bash
nohup java -jar config-server-1.0-SNAPSHOT.jar --spring.profiles.active=native,test &
```

```bash
sudo docker run -itd --name config-server -p 8080:8080 \
    -v ~/config:/usr/local/eshop/config:ro \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/config-server --spring.profiles.active=native,product 
```