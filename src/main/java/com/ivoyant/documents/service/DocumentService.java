package com.ivoyant.documents.service;

import com.ivoyant.documents.entity.Document;
import com.ivoyant.documents.exception.ResourceNotFoundException;
import com.ivoyant.documents.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
        try {
            log.info("Saving document for employeeId: {}", document.getEmployeeId());
            Document savedDocument = documentRepository.save(document);
            log.info("Document saved successfully");
            return savedDocument;
        } catch (Exception e) {
            log.error("Error occurred while saving document for employeeId: {}", document.getEmployeeId(), e);
            throw new RuntimeException("Failed to save document", e);
        }
    }


    public List<Document> getDocumentsByEmployeeId(Long employeeId) {
        log.info("Finding documents for employeeId: " + employeeId);

        List<Document> documents = documentRepository.findByEmployeeId(employeeId);

        if (documents == null || documents.isEmpty()) {
            log.error("No documents found for employeeId: " + employeeId);
            throw new ResourceNotFoundException("No documents found for employeeId: " + employeeId);
        }

        log.info("Found " + documents.size() + " documents for employeeId: " + employeeId);
        return documents;
    }
}
