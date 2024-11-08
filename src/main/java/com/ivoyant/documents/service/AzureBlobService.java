package com.ivoyant.documents.service;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public String uploadFile(MultipartFile file) {
        String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);

            blobClient.upload(file.getInputStream(), file.getSize(), true);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
            blobClient.setHttpHeaders(headers);

            return blobClient.getBlobUrl();
        } catch (BlobStorageException | IOException e) {
            throw new RuntimeException("Error uploading file to Azure Blob Storage", e);
        }
    }
}
