package com.example.demo.models;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@Entity
@Table(name = "file")
@JsonInclude(Include.NON_NULL)
public class FileModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    @JsonIgnore
    private Long id;
    
    @Column(nullable = false)
    private String fileName;

    @Column(name="hashSha256")
    private String hashSha256;
    
    @Column(name="hashSha512")
    private String hashSha512;

    

    
    @Column
    @JsonInclude(Include.NON_NULL)
    private Date lastUpload;

    
   
    // Setters & Getters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHashSha256() {
        return hashSha256;
    }
    public void setHashSha256(String hashSha256) {
        this.hashSha256 = hashSha256;
    }

    public String getHashSha512() {
        return hashSha512;
    }
    public void setHashSha512(String hashSha512) {
        this.hashSha512 = hashSha512;
    }

    public Date getLastUpload() {
        return lastUpload;
    }
    public void setLastUpload(Date lastUpload) {
        this.lastUpload = lastUpload;
    }

}
