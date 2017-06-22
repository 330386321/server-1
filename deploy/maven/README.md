
```bash    
sudo docker run -it --rm --name maven \
    -v ~/src/server:/usr/src/maven \
    -v ~/.m2:/root/.m2 \
    -w /usr/src/maven \
    maven:3.5.0-jdk-8-alpine mvn clean package
```
