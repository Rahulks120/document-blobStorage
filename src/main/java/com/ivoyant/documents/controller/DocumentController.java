package com.ivoyant.documents.controller;

import com.ivoyant.documents.entity.Document;
import com.ivoyant.documents.entity.DocumentType;
import com.ivoyant.documents.service.AzureBlobService;
import com.ivoyant.documents.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("employeeId") Long employeeId,
                                                 @RequestParam("type") DocumentType type,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || employeeId == null || description.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        String blobUrl = azureBlobService.uploadFile(file);

        Document document = new Document();
        document.setEmployeeId(employeeId);
        document.setType(type);
        document.setDescription(description);
        document.setAttachmentPath(blobUrl);
        document.setDate(LocalDate.now());

        documentService.saveDocument(document);

        return ResponseEntity.ok("Document uploaded successfully!");
    }

    @GetMapping("/{employeeId}")
    public List<Document> getDocumentsByEmployeeId(@PathVariable Long employeeId) {
        return documentService.getDocumentsByEmployeeId(employeeId);
    }
}
