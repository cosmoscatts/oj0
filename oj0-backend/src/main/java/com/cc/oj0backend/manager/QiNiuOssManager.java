package com.cc.oj0backend.manager;

import com.cc.oj0backend.common.ErrorCode;
import com.cc.oj0backend.config.QiNiuClientConfig;
import com.cc.oj0backend.exception.BusinessException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 七牛云对象存储操作
 */
@Component
public class QiNiuOssManager {
    @Resource
    private  QiNiuClientConfig qiNiuClientConfig;
    private  UploadManager uploadManager;
    private  String token;
    private  Auth auth;
    private  BucketManager bucketManager;

    @PostConstruct
    public void init() {
        uploadManager = new UploadManager(new Configuration(Zone.zone0()));
        auth = Auth.create(qiNiuClientConfig.getAccessKey(), qiNiuClientConfig.getSecretKey());
        bucketManager = new BucketManager(auth, new Configuration(Zone.zone2()));
        token = auth.uploadToken(qiNiuClientConfig.getBucketName());
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
