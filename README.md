# smthit4
Java Spring Springboot2.x 微服务项目快速脚手架    

# 快速发布
```bash
mvn clean deploy -P release  
```

# 本地快速使用
* 安装到本地库
```bash
mvn clean compile install
```

* 编译升级版本
```bash
mvn release:prepare
mvn release:prepare -Dresume=false
mvn release:clean release:prepare
```

## 更新记录
* 2023.09 smthit-beetsql 增加多租户
* 
* 
