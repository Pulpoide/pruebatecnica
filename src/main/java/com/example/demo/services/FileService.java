package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.dto.FileInfoDTO;
import com.example.demo.dto.PostResponseDTO;
import com.example.demo.models.FileModel;
import com.example.demo.repositories.FileRepository;
import com.example.demo.utils.HashUtil;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    // Métodos:
    public ArrayList<FileModel> obtenerArchivos() {
        return (ArrayList<FileModel>) fileRepository.findAll();
    }

    public ArrayList<FileModel> getFilesByHash(String hash, String hashType) {

        switch (hashType) {
            case "SHA-512":
            case "SHA-256":
                break;
            default:
                throw new BadRequestException(
                        "Excepcion de nombre de hasheo incorrecto. Solo disponibles 'SHA-512' y 'SHA-256'");
        }
        if (hash.length() <= 0) {
            throw new BadRequestException("El hash no debe estar vacio.");
        }
        ArrayList<FileModel> files = fileRepository.getFileByHash(hash);
        if (files.size() <= 0) {
            throw new NotFoundException("No hay ningún documento con ese nombre.");
        }
        return files;
    }

    public PostResponseDTO saveFile(MultipartFile file, String hashType) throws Exception {
        String hash = hashType.toUpperCase();
        byte[] fileBytes;
        String fileHashed;

        try {
            if (file.isEmpty()) {
                throw new BadRequestException("No se subieron archivos o el se pasó mal el parámetro");
            }
            fileBytes = file.getBytes();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        switch (hash) {
            case "SHA-512":
            case "SHA-256":
                fileHashed = HashUtil.getHash(fileBytes, hash);
                break;
            default:
                throw new BadRequestException(
                        "Excepcion de nombre de hasheo incorrecto. Solo disponibles 'SHA-512' y 'SHA-256'");
        }

        FileModel filemodel = new FileModel();
        ArrayList<FileModel> models = fileRepository.getFileByHash(fileHashed);

        if (models.size() > 0) {
            filemodel.setLastUpload(new Date());
        }

        if (hash.equals("SHA-256")) {
            filemodel.setHashSha256(fileHashed);
        } else if (hash.equals("SHA-512")) {
            filemodel.setHashSha512(fileHashed);
        }
        filemodel.setFileName(file.getOriginalFilename());

        FileModel newFile = fileRepository.save(filemodel);
        models.add(newFile);

        // Builder
        List<FileInfoDTO> documents = new ArrayList<>();

        for (FileModel fmodel : models) {
            if (hash.equals("SHA-256")) {
                documents.add(FileInfoDTO.builder().fileName(fmodel.getFileName()).hash(fmodel.getHashSha256())
                        .lastUpload(fmodel.getLastUpload()).build());
            } else if (hash.equals("SHA-512")) {
                documents.add(FileInfoDTO.builder().fileName(fmodel.getFileName()).hash(fmodel.getHashSha512())
                        .lastUpload(fmodel.getLastUpload()).build());
            }
        }
        return PostResponseDTO.builder().algorithm(hash).documents(documents).build();
    }

    public void deleteAll() {
        this.fileRepository.deleteAll();
    }

}
