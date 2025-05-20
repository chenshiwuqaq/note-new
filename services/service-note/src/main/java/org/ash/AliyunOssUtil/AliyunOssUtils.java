package org.ash.AliyunOssUtil;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Component
public class AliyunOssUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    /**
     * 上传文件到阿里云 OSS
     *
     * @param file     上传的文件
     * @param filePath 文件在 OSS 中的路径（如 "uploads/first.md"）
     * @return 文件的访问 URL
     */
    public String uploadFile(MultipartFile file, String filePath) throws IOException {
        // 创建 OSS 客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try (InputStream inputStream = file.getInputStream()) {
            // 打印文件信息
            System.out.println("上传文件: " + filePath);
            System.out.println("文件大小: " + file.getSize() + " bytes");

            // 上传文件
            ossClient.putObject(new PutObjectRequest(bucketName, filePath, inputStream));

            // 返回文件的访问 URL
            return "https://" + bucketName + "." + endpoint + "/" + filePath;
        } finally {
            // 关闭 OSS 客户端
            ossClient.shutdown();
        }
    }

    /**
     * 生成唯一的文件名
     *
     * @param originalFilename 原始文件名
     * @return 唯一的文件名
     */
    public String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "-" + originalFilename;
    }
    //这里的filename就指node的id
    public String getFileContent(String filePath) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, filePath));
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content.toString();
            }
        } catch (OSSException | ClientException | IOException e) {
            return null;
        } finally {
            ossClient.shutdown();
        }
    }
}
