package com.aws.samples.djl.spring.common;

import java.util.List;

public class MyFilesListing {

    public List<MyObjectSummaries> objectSummaries;

    public MyFilesListing(List<MyObjectSummaries> objectSummaries) {
        this.objectSummaries = objectSummaries;
    }

    public List<MyObjectSummaries> getObjectSummaries() {
        return objectSummaries;
    }

    public void setObjectSummaries(List<MyObjectSummaries> objectSummaries) {
        this.objectSummaries = objectSummaries;
    }
}
