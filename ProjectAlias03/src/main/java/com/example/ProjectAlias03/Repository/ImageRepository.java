package com.example.ProjectAlias03.Repository;


import com.example.ProjectAlias03.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    Optional<ImageEntity> findById(String imageId);

}
