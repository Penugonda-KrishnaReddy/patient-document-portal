package com.example.patientdocs.controller;

import com.example.patientdocs.model.Document;
import com.example.patientdocs.service.DocumentService;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/documents")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) { this.service = service; }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.getOriginalFilename().endsWith(".pdf"))
            return ResponseEntity.badRequest().body("Only PDF allowed");
        Document saved = service.store(file);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Document> list() { return service.listAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception {
        Document doc = service.getById(id).orElse(null);
        if(doc == null) return ResponseEntity.notFound().build();
        File f = new File(doc.getFilePath());
        Resource resource = new FileSystemResource(f);
        String contentType = Files.probeContentType(f.toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + doc.getOriginalFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        boolean ok = service.deleteById(id);

        if (!ok) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Document with ID " + id + " not found in database");
        }

        return ResponseEntity.ok(" Document deleted successfully");
    }
}