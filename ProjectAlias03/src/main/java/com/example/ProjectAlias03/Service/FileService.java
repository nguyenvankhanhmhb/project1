package com.example.ProjectAlias03.Service;

import com.example.ProjectAlias03.Entity.FileEntity;
import com.example.ProjectAlias03.Repository.FileRepository;
import com.example.ProjectAlias03.Service.Imp.FileSeriveImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class FileService implements FileSeriveImp {

    @Autowired
    private FileRepository fileRepository;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String typeFile = file.getContentType();
        byte[] data = file.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setType(typeFile);
        fileEntity.setData(data);

        FileEntity result = (FileEntity) fileRepository.save(fileEntity);
        if(result != null) {
            return "Upload file thanh cong" + fileName;
        }
        return "Upload file that bai";


    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        FileEntity file = fileRepository.findByName(fileName);

        return file.getData();
    }
}
