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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class S3ImageDownloader {

    private static final Logger LOG = LoggerFactory.getLogger(S3ImageDownloader.class);

    private final String bucketName; // myBucket

    private final String folder; // inbox

    public S3ImageDownloader(String bucketName, String folder) {
        this.bucketName = bucketName;
        this.folder = folder == null ? "" : folder.concat("/");
    }

    public InputStream downloadStream(String fileName, String inOrOutbox) throws IOException {
        LOG.info("Downloading {} from LOCAL bucket {}...\n", fileName, bucketName);
//        return s3.getObject(bucketName, key).getObjectContent();

        String path = fileName;
        if (!path.contains("outbox") && !path.contains("inbox")) {
            path = inOrOutbox + "/" + path;
        }
        if (!path.contains(bucketName)) {
            path = bucketName + "/" + path;
        }
        return new FileInputStream(path);
    }

    public MyFilesListing listFolder(String folder) {
//        return s3.listObjects(bucketName, folder);
        String pathname = bucketName + "/" + folder;
        File directory = new File(pathname);
        return new MyFilesListing(
                Arrays.stream(Objects.requireNonNull(directory.list()))
                        .map(s -> new MyObjectSummaries(pathname + "/" + s))
                        .collect(Collectors.toList()));
    }

}
