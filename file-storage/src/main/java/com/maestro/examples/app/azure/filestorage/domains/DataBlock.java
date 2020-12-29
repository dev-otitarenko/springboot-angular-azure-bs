package com.maestro.examples.app.azure.filestorage.domains;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about uploaded file chunk
 */
@Data
@Builder
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
