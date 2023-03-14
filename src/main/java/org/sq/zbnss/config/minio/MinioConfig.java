package org.sq.zbnss.config.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sq.zbnss.uitl.MinioUtils;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioUtils creatMinioClient() {
        return new MinioUtils(endpoint, accessKey, secretKey);
    }
}
