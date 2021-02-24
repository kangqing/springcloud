> 使用 keytool 生成 RSA 证书 jwt.jks，复制到 resource 目录下，在 JDK 的 bin 目录下使用如下命令即可
```bash
keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
```
