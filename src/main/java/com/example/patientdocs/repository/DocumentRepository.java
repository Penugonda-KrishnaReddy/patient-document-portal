package com.example.patientdocs.repository;

import com.example.patientdocs.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> { }
