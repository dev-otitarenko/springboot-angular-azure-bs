package com.maestro.examples.app.azure.filestorage.controllers;

import com.maestro.examples.app.azure.filestorage.domains.DataBlock;
import com.maestro.examples.app.azure.filestorage.domains.FilePrm;
import com.maestro.examples.app.azure.filestorage.services.AzureBlobStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bs")
@Slf4j
public class AzureBlobStorageController {
    private AzureBlobStorageService fileService;

    public AzureBlobStorageController(AzureBlobStorageService fileService) {
        this.fileService = fileService;
    }

    /**
     * Creating container in Azure Blob Storage before uploading document file
     *
     * @param idContainer document id
     * @return String
     */
    @PostMapping(value = "/{idContainer}/files/before-upload")
    public String beforeUploadDocFile(@PathVariable String idContainer) {
        return fileService.beforeUpload(idContainer);
    }

    /**
     * Uploading document file chunk into Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @PutMapping(value = "{idContainer}/files/{idFile}")
    public void uploadDocFile(@PathVariable String idContainer, @PathVariable String idFile, @RequestBody @Validated DataBlock data) {
        fileService.uploadFile(idContainer, idFile, data);
    }

    /**
     * Completing uploading file;s chunk into Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @PostMapping(value = "/{idContainer}/files/{idFile}")
    public void completeUploadDocFile(@PathVariable String idContainer, @PathVariable String idFile, @RequestBody @Validated FilePrm data) {
        fileService.completeUploadFile(idContainer, idFile, data);
    }

    /**
     * Deleting file from Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @DeleteMapping(value = "{idContainer}/files/{idFile}")
    public void deleteDocFile(@PathVariable String idContainer, @PathVariable String idFile) {
        fileService.deleteFile(idContainer, idFile);
    }

    /**
     * Getting document file link from Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @GetMapping(value = "/{idContainer}/files/{idFile}/link")
    public String getLinkDocFile(@PathVariable String idContainer, @PathVariable String idFile) {
        return fileService.getLinkFile(idContainer, idFile);
    }
}
