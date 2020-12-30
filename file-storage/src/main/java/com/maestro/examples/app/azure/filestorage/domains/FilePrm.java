package com.maestro.examples.app.azure.filestorage.domains;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Information about file to complete upload to the storage
 */
@Data
@Builder
public class FilePrm {
    /**
     * The file name
     */
    private String name;
    /**
     * The file type
     */
    private String type;
    /**
     * Base64 block ids to complete upload
     */
    private List<String> base64BlockIds;
}