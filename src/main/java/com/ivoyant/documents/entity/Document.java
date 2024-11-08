package com.ivoyant.documents.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    private String description;

    @Column(name = "attachment_path")
    private String attachmentPath;

    private LocalDate date;
}
