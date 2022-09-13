package com.example.demo.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.models.FileModel;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {

        @Query(value = "select * from file where hash_sha512 = :hash1 OR hash_sha256 = :hash1 ", nativeQuery = true)
        public ArrayList<FileModel> getFileByHash(@Param("hash1") String hash1);

}
