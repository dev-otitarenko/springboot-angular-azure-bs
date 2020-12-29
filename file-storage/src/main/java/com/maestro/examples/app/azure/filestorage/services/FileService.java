package com.maestro.examples.app.azure.filestorage.services;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.sas.BlobContainerSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.maestro.examples.app.azure.filestorage.records.DataBlock;
import com.maestro.examples.app.azure.filestorage.records.FilePrm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Interaction with Azure Blob Storage
 */
@Service
@Slf4j
public class FileService {
    @Value("${app.azure-account.name}")
    private String accountName;
    @Value("${app.azure-account.key}")
    private String accountKey;
    @Value("${app.offsetHours}")
    private Integer offsetHours;
    MessageDigest md;


    public FileService() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("FileServiceInit", e);
        }
    }

    /**
     * Initializes a  BlobContainerClient object pointing to the specified container.
     *
     * @param containerName The name of the container to point to.
     * @return A BlobContainerClient object pointing to the specified container.
     */
    private BlobContainerClient getBlobContainerClient(final String containerName) {
        final String connectString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", this.accountName, this.accountKey);
        return new BlobServiceClientBuilder()
                .connectionString(connectString)
                .buildClient()
                .getBlobContainerClient(containerName);
    }

    /**
     * Creating container in Azure Blob Storage before uploading document file
     *
     * @param id document id
     * @return file id to be uploaded
     */
    public String beforeUploadDocFile(final String id) {
        BlobContainerClient container = getBlobContainerClient(id.toLowerCase());
        if (!container.exists()) container.create();
        return UUID.randomUUID().toString();
    }

    /**
     * Uploading document file chunk into Azure Blob Storage
     *
     * @param iddoc document id
     * @param id    file id
     * @param data  file chunk data
     */
    public void uploadDocFile(final String iddoc, final String id, final DataBlock data) {
        BlobContainerClient container = getBlobContainerClient(iddoc.toLowerCase());

        BlockBlobClient blobClient = container.getBlobClient(id).getBlockBlobClient();
        byte[] byteArr = DatatypeConverter.parseBase64Binary(data.getData());
        md.update(byteArr);
        byte[] digest = md.digest();
        blobClient.stageBlockWithResponse(data.getBase64BlockId(), new ByteArrayInputStream(byteArr), byteArr.length, digest, null, null, null);
    }

    /**
     * Commit blocks in Azure Blob Storage after document file has been uploaded
     *
     * @param iddoc document id
     * @param id    file id
     * @param data  FileDto data
     */
    public void completeUploadDocFile(final String iddoc, final String id, final FilePrm data) {
        BlobContainerClient container = getBlobContainerClient(iddoc.toLowerCase());
        BlockBlobClient blobClient = container.getBlobClient(id).getBlockBlobClient();
        BlobHttpHeaders blobHTTPHeaders = new BlobHttpHeaders().setContentType(data.getType()).setContentDisposition("filename=" + data.getName());
        blobClient.commitBlockListWithResponse(data.getBase64BlockIds(), blobHTTPHeaders, null, null, null, null, null);
    }

    /**
     * Deleting document file from Azure Blob Storage
     *
     * @param iddoc document id
     * @param id    file id
     */
    public void deleteDocFile(final String iddoc, final String id) {
        BlobContainerClient container = getBlobContainerClient(iddoc.toLowerCase());
        container.getBlobClient(id).getBlockBlobClient().delete();
    }

    /**
     * Getting document file link from Azure Blob Storage
     *
     * @param iddoc document id
     * @param id    file id
     * @return link to the document file
     */
    public String getLinkDocFile(final String iddoc, final String id) {
        BlobContainerClient container = getBlobContainerClient(iddoc.toLowerCase());
        BlockBlobClient blobClient = container.getBlobClient(id).getBlockBlobClient();
        OffsetDateTime offsetDateTime = OffsetDateTime.now().plusHours(offsetHours);
        BlobServiceSasSignatureValues sas = new BlobServiceSasSignatureValues(offsetDateTime,
                BlobContainerSasPermission.parse("r"));
        String sasToken = blobClient.generateSas(sas);
        String link = blobClient.getBlobUrl() + "?" + sasToken;
        return link;
    }

    /**
     * Deleting all document file from Azure Blob Storage
     *
     * @param iddoc document id
     */
    public void deleteDocument(final String iddoc) {
        try {
            BlobContainerClient container = getBlobContainerClient(iddoc.toLowerCase());
            container.delete();
        } catch (BlobStorageException e) {
            if (e.getStatusCode() != 404) {
                log.error("deleteDocument exception", e);
                throw e;
            } else {
                log.debug("container (document) not found id={}", iddoc);
            }
        }
    }
}
