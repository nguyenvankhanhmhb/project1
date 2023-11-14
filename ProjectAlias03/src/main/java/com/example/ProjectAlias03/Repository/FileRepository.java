package com.example.ProjectAlias03.Repository;

import com.example.ProjectAlias03.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer> {
    FileEntity findByName(String fileName);

}
