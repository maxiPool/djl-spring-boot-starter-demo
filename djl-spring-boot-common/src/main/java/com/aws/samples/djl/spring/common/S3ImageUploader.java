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

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;

public class S3ImageUploader {
    private static final Logger LOG = LoggerFactory.getLogger(S3ImageDownloader.class);


    private final String bucketName;

    private final String folder;

//    private static final String S3REF = "https://%s.s3.amazonaws.com/%s";


    public S3ImageUploader(String bucketName, String folder) {
        this.bucketName = bucketName;
        this.folder = folder == null ? "" : folder.concat("/");
    }

    public String upload(RenderedImage image, String file) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        byte[] buf = os.toByteArray();
        try (var is = new ByteArrayInputStream(buf)) {
            return upload(is, buf.length, file);
        }
    }

    public String upload(InputStream inputStream, long contentLength, String file) throws IOException {
        String key = bucketName + "/" + folder + file;
        LOG.info("Uploading {} to S3 bucket {}...\n", key, bucketName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
//        byte[] resultByte = DigestUtils.md5(inputStream);
//        String streamMD5 = new String(Base64.encodeBase64(resultByte));
//        metadata.setContentMD5(streamMD5);
//        s3.putObject(bucketName, key, inputStream, metadata);

        writeImage(key, inputStream);

        return key;
    }

    public static void writeImage(String fileName, InputStream imageStream) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}
