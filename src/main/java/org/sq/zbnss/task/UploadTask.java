package org.sq.zbnss.task;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import org.sq.zbnss.uitl.MinioUtils;

public class UploadTask extends Thread {


    private String bucketName;

    private MultipartFile multipartFile;

    private String code;


    public UploadTask(String bucketName, MultipartFile multipartFile, String code) {
        this.bucketName = bucketName;
        this.multipartFile = multipartFile;
        this.code = code;
    }

    @SneakyThrows
    @Override
    public void run() {
        MinioUtils.putObject(bucketName,multipartFile,code);
    }
}
