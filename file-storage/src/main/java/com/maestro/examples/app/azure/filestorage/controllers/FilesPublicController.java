package com.maestro.examples.app.azure.filestorage.controllers;

import com.maestro.examples.app.azure.filestorage.records.DataBlock;
import com.maestro.examples.app.azure.filestorage.records.FilePrm;
import com.maestro.examples.app.azure.filestorage.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class FilesPublicController {
    private FileService fileService;

    public FilesPublicController(FileService fileService) {
        this.fileService = fileService;
    }

//    @ApiOperation(value = "Creating container in Azure Blob Storage before uploading document file", response = String.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    paramType = "header",
//                    value = "Bearer token",
//                    required = true,
//                    dataType = "string",
//                    defaultValue = "Bearer ....."
//            ),
//            @ApiImplicitParam(
//                    name = "id",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "document id"
//            )
//    })
    @PostMapping(value = "docs/{id}/files/before-upload")
    public String beforeUploadDocFile(@PathVariable String id) {
        return fileService.beforeUploadDocFile(id);
    }

//    @ApiOperation(value = "Uploading document file chunk into Azure Blob Storage")
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    paramType = "header",
//                    value = "Bearer token",
//                    required = true,
//                    dataType = "string",
//                    defaultValue = "Bearer ....."
//            ),
//            @ApiImplicitParam(
//                    name = "iddoc",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "document id"
//            ),
//            @ApiImplicitParam(
//                    name = "id",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "file id"
//            ),
//            @ApiImplicitParam(
//                    name = "data",
//                    paramType = "body",
//                    required = true,
//                    dataType = "DataBlock",
//                    value = "DataBlock"
//            )
//    })
    @PutMapping(value = "docs/{iddoc}/files/{id}")
    public void uploadDocFile(@PathVariable String iddoc, @PathVariable String id, @RequestBody @Validated DataBlock data) {
        fileService.uploadDocFile(iddoc, id, data);
    }

//    @ApiOperation(value = "Commit blocks in Azure Blob Storage after document file has been uploaded")
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    paramType = "header",
//                    value = "Bearer token",
//                    required = true,
//                    dataType = "string",
//                    defaultValue = "Bearer ....."
//            ),
//            @ApiImplicitParam(
//                    name = "iddoc",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "document id"
//            ),
//            @ApiImplicitParam(
//                    name = "id",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "file id"
//            ),
//            @ApiImplicitParam(
//                    name = "data",
//                    paramType = "body",
//                    required = true,
//                    dataType = "FileDto",
//                    value = "File metadata"
//            )
//    })
    @PostMapping(value = "docs/{iddoc}/files/{id}")
    public void completeUploadDocFile(@PathVariable String iddoc, @PathVariable String id, @RequestBody @Validated FilePrm data) {
        fileService.completeUploadDocFile(iddoc, id, data);
    }

//    @ApiOperation(value = "Deleting document file from Azure Blob Storage")
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    paramType = "header",
//                    value = "Bearer token",
//                    required = true,
//                    dataType = "string",
//                    defaultValue = "Bearer ....."
//            ),
//            @ApiImplicitParam(
//                    name = "iddoc",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "document id"
//            ),
//            @ApiImplicitParam(
//                    name = "id",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "file id"
//            )
//    })
    @DeleteMapping(value = "docs/{iddoc}/files/{id}")
    public void deleteDocFile(@PathVariable String iddoc, @PathVariable String id) {
        fileService.deleteDocFile(iddoc, id);
    }

//    @ApiOperation(value = "Getting document file link from Azure Blob Storage", response = String.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    paramType = "header",
//                    value = "Bearer token",
//                    required = true,
//                    dataType = "string",
//                    defaultValue = "Bearer ....."
//            ),
//            @ApiImplicitParam(
//                    name = "id",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "document id"
//            ),
//            @ApiImplicitParam(
//                    name = "idFile",
//                    paramType = "path",
//                    required = true,
//                    dataType = "String",
//                    value = "file id"
//            )
//    })
    @GetMapping(value = "docs/{id}/files/{idFile}/link")
    public String getLinkDocFile(@PathVariable String id, @PathVariable String idFile) {
        return fileService.getLinkDocFile(id, idFile);
    }
}
