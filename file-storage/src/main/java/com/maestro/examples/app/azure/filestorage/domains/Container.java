package com.maestro.examples.app.azure.filestorage.domains;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
/*
* Information about a azure blob container
 */
public class Container {
    // Container name
    private String name;
    //  BlobContainerItemProperties.leaseState
    private String leaseState;
    //  BlobContainerItemProperties.leaseStatus
    private String leaseStatus;
    //  BlobContainerItemProperties.lastModified
    private OffsetDateTime lastModified;
    //  BlobContainerItemProperties.ETag
    private String etag;
}
