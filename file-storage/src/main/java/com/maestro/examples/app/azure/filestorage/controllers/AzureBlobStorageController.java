package com.maestro.examples.app.azure.filestorage.controllers;

import com.azure.storage.blob.models.BlobContainerItemProperties;
import com.azure.storage.blob.models.BlobItem;
import com.maestro.examples.app.azure.filestorage.domains.Container;
import com.maestro.examples.app.azure.filestorage.domains.DataBlock;
import com.maestro.examples.app.azure.filestorage.domains.FilePrm;
import com.maestro.examples.app.azure.filestorage.services.AzureBlobStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bs")
@Slf4j
public class AzureBlobStorageController {
    private final AzureBlobStorageService fileService;

    public AzureBlobStorageController(AzureBlobStorageService fileService) {
        this.fileService = fileService;
    }

    /**
     * Getting document file link from Azure Blob Storage
     *
     * @return List of blob's name
     */
    @GetMapping(value = "/")
    public List<Container> listContainers() {
        return fileService.listContainers()
                .stream()
                .map(itm -> {
                    BlobContainerItemProperties properties = itm.getProperties();
                    return Container
                                    .builder()
                                        .name(itm.getName())
                                        .leaseState(properties.getLeaseState().toString())
                                        .leaseStatus(properties.getLeaseStatus().toString())
                                        .lastModified(properties.getLastModified())
                                        .etag(properties.getETag())
                                    .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * Creating container in Azure Blob Storage before uploading document file
     *
     * @param idContainer document id
     * @return String
     */
    @PostMapping(value = "/{idContainer}/files/before-upload")
    public String beforeUpload(@PathVariable String idContainer) {
        return fileService.beforeUpload(idContainer);
    }

    /**
     * Uploading document file chunk into Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @PutMapping(value = "{idContainer}/files/{idFile}")
    public void uploadFile(@PathVariable String idContainer, @PathVariable String idFile, @RequestBody @Validated DataBlock data) {
        fileService.uploadFile(idContainer, idFile, data);
    }

    /**
     * Completing uploading file;s chunk into Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @PostMapping(value = "/{idContainer}/files/{idFile}")
    public void completeUpload(@PathVariable String idContainer, @PathVariable String idFile, @RequestBody @Validated FilePrm data) {
        fileService.completeUploadFile(idContainer, idFile, data);
    }

    /**
     * Deleting file from Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @DeleteMapping(value = "{idContainer}/files/{idFile}")
    public void deleteFile(@PathVariable String idContainer, @PathVariable String idFile) {
        fileService.deleteFile(idContainer, idFile);
    }

    /**
     * Getting document file link from Azure Blob Storage
     *
     * @param idContainer document id
     * @param idFile file id
     */
    @GetMapping(value = "/{idContainer}/files/{idFile}/link")
    public String getLinkFile(@PathVariable String idContainer, @PathVariable String idFile) {
        return fileService.getLinkFile(idContainer, idFile);
    }

    /**
     * Getting document file link from Azure Blob Storage
     *
     * @param idContainer document id
     * @return List of blob's name
     */
    @GetMapping(value = "/{idContainer}/files")
    public List<String> listBlobs(@PathVariable String idContainer) {
        return fileService.listBlobs(idContainer)
                    .stream()
                    .map(BlobItem::getName)
                    .collect(Collectors.toList());
    }

    /**
     * Deleting a container from Azure Blob Storage
     *
     * @param idContainer document id
     */
    @DeleteMapping(value = "/{idContainer}")
    public void deleteContainer(@PathVariable String idContainer) {
        fileService.deleteContainer(idContainer);
    }
}
