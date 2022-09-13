package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.models.FileModel;
import com.example.demo.services.FileService;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/documents")
    public ArrayList<FileModel> obtenerArchivos() {
        return fileService.obtenerArchivos();
    }

    @GetMapping(path = "/documents/hash")
    public ResponseEntity<?> getFileByHash(@RequestParam("hash") String hash,
            @RequestParam("hashType") String hashType) {
        return new ResponseEntity<>(fileService.getFilesByHash(hash, hashType), HttpStatus.OK);
    }

    // uploadFile
    @PostMapping("/documents/hash")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("hashType") String hashType) throws Exception {
        PostResponseDTO response = fileService.saveFile(file, hashType);
        return new ResponseEntity<PostResponseDTO>(response, HttpStatus.CREATED);
    }

    // Para borrar TODOS los archivos.
    @RequestMapping("deleteall")
    public ResponseEntity<?> deleteFiles() {
        try {
            fileService.deleteAll();
            System.out.println("Se eliminaron todos los archivos con éxito..");
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminaron todos los archivos con éxito.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException("No se pudieron borrar los archivos.");
        }

    }

}
