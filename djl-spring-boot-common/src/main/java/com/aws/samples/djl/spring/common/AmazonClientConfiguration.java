/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.aws.samples.djl.spring.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Consider spring cloud for aws as a potential option to configure.
 */
public class AmazonClientConfiguration {

//    private static final Logger LOG = LoggerFactory.getLogger(AmazonClientConfiguration.class);

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.upload-folder}")
    private String uploadFolder;

    @Value("${aws.s3.download-folder}")
    private String downloadFolder;


//    @Bean
//    public AmazonS3 s3() {
//        LOG.info("Initializing aws provider with custom chain with WebIdentity provider");
//        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
//                .withCredentials(new AWSCredentialsProviderChain(WebIdentityTokenCredentialsProvider.create(),
//                        new EnvironmentVariableCredentialsProvider(),
//                        new ProfileCredentialsProvider()))
//                .build();
//    }

    @Bean
    public S3ImageDownloader downloader() {
        return new S3ImageDownloader(bucketName, downloadFolder);
    }

    @Bean
    public S3ImageUploader uploader() {
        return new S3ImageUploader(bucketName, uploadFolder);
    }
}


