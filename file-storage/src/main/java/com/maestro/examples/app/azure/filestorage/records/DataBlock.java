package com.maestro.examples.app.azure.filestorage.records;

import lombok.Data;

/**
 * Information about uploaded file chunk
 */
@Data
public class DataBlock {
    /**
     * Base64 block id to upload
     */
    private String base64BlockId;
    /**
     * Base64 data to upload
     */
    private String data;
}
