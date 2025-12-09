package com.example.patientdocs.service;

import com.example.patientdocs.model.Document;
import com.example.patientdocs.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository repo;
    private final Path uploadDir;

    public DocumentService(DocumentRepository repo, @Value("${file.upload-dir}") String dir) {
        this.repo = repo;
        this.uploadDir = Paths.get(dir).toAbsolutePath();
        try { Files.createDirectories(uploadDir); }
        catch(IOException e){ throw new RuntimeException("Upload dir creation failed"); }
    }

    public Document store(MultipartFile file) throws IOException {
        String original = file.getOriginalFilename();
        String stored = System.currentTimeMillis()+"_"+original;
        Path target = uploadDir.resolve(stored);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        Document doc = new Document();
        doc.setOriginalFilename(original);
        doc.setStoredFilename(stored);
        doc.setFilePath(target.toString());
        doc.setFileSize(file.getSize());
        return repo.save(doc);
    }

    public List<Document> listAll() { return repo.findAll(); }

    public Optional<Document> getById(Long id) { return repo.findById(id); }

    public boolean deleteById(Long id) {
        Optional<Document> opt = repo.findById(id);
        if(opt.isEmpty()) return false;
        Document doc = opt.get();
        File f = new File(doc.getFilePath());
        if(f.exists()) f.delete();
        repo.deleteById(id);
        return true;
    }
}
