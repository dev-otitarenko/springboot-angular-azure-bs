package com.maestro.examples.app.azure.filestorage.records;

import lombok.Data;

import java.util.List;

/**
 * Dto class with information about file to complete upload to the storage
 */
@Data
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