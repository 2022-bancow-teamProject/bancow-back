package com.bancow.bancowback.infra.ncp;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bancow.bancowback.domain.common.exception.ErrorCode;
import com.bancow.bancowback.domain.common.exception.NcpException;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class NcpService {
    private AmazonS3 s3Client;

    @Value("${cloud.ncp.url.endPoint}")
    private String endPoint;
    @Value("${cloud.ncp.url.regionName}")
    private String regionName;
    @Value("${cloud.ncp.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.ncp.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.ncp.s3.bucketName}")
    private String bucketName;

    @PostConstruct
    public void setS3Client(){
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public String objectUpload(@NotNull final String folderName,@NotNull final MultipartFile file) throws IOException {
        validImageExtensionCheck(file);
        String fileName = getUuidImageName(file.getOriginalFilename());
        String filePath = folderName + "/"+ getUuidImageName(file.getOriginalFilename());
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            throw new NcpException(ErrorCode.NOT_S3_ERROR,"S3 업로드가 안됨");
        }

        return getFileUrl(folderName, fileName);
    }

    public String getFileUrl(String bucketName,String fileName) {
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    public String getUuidImageName(String imageName){
        return UUID.randomUUID() + imageName;
    }

    public void validImageExtensionCheck(MultipartFile file ){
        String fileName = file.getOriginalFilename();
        if(fileName.endsWith(".png")||fileName.endsWith(".jpeg")||fileName.endsWith(".jpg")){
        } else {
            throw new NcpException(ErrorCode.NOT_IMAGE,"파일 확인하세요");
        }
    }

}
