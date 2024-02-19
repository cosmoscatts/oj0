---
outline: deep
---

# 第三方图床

<br>

> 此项目中使用的是七牛云图床，可根据需要自行更换

## 准备工作

网上很多教程，可参考 [搭建七牛云图床并配置 https](https://blog.csdn.net/u014246801/article/details/129738839) 这篇文章。建立一个存储空间，得到 `AccessKey` 和 `SecretKey`。

## 代码

### 七牛云配置类

::: code-group
```java [QiNiuClientConfig]
/**
 * 七牛云对象存储客户端
 */
@Configuration
@ConfigurationProperties(prefix = "oss.qiniu")
@Data
public class QiNiuClientConfig {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 七牛域名 domain
     */
    private String url;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 目录名称
     */
    private String dir;
}
```

```yml [application.yml]
oss:
  qiniu:
    url: https://xxx.xxx.xxx
    accessKey: xxxxxxxx
    secretKey: xxxxxxxx
    bucketName: xxxxxxxx
    dir: xxxxxxxx/
```
:::

搭配 `Spring` 自动配置使用，在 `application.yml` 填写参数。

### 编写存储操作工具类

::: details QiNiuOssManager.java
```java 
/**
 * 七牛云对象存储操作
 */
@Component
public class QiNiuOssManager {
    @Resource
    private  QiNiuClientConfig qiNiuClientConfig;
    private  UploadManager uploadManager;
    private  Auth auth;
    private  BucketManager bucketManager;

    @PostConstruct
    public void init() {
        uploadManager = new UploadManager(new Configuration(Zone.zone0()));
        auth = Auth.create(qiNiuClientConfig.getAccessKey(), qiNiuClientConfig.getSecretKey());
        bucketManager = new BucketManager(auth, new Configuration(Zone.zone2()));
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            String fileName = getFileRandomName(originalFileName);
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            String token = auth.uploadToken(qiNiuClientConfig.getBucketName());
            Response result = uploadManager.put(inputStream, fileName, token, null, null);
            if (!result.isOK()) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传文件至七牛云出错");
            }
            return getFileUrl(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件 Url 地址
     */
    public String getFileUrl(String fileKey) {
        String encodedFileName;
        String finalUrl = null;
        try {
            encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", this.qiNiuClientConfig.getUrl(), encodedFileName);
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    /**
     * 删除文件
     */
    public boolean removeFile(String bucketName, String fileKey) {
        try {
            bucketManager.delete(bucketName, fileKey);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 生成文件名工具方法
     */
    private String getFileRandomName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (fileName.isEmpty() || index == -1){
            throw new IllegalArgumentException();
        }
        String suffix = fileName.substring(index);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String path = qiNiuClientConfig.getDir() + uuid + suffix;
        return path;
    }
}
```
:::

### api 接口

::: details UploadController.java
```java
/**
 * 上传文件至七牛云接口
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    @Resource
    private QiNiuOssManager qiNiuOssManager;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String filePath = qiNiuOssManager.uploadFile(file);
        if (filePath == null || filePath.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传文件至七牛云出错");
        }
        return ResultUtils.success(filePath);
    }
}
```
:::
