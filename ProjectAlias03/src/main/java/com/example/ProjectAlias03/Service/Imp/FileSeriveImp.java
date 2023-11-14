package com.example.ProjectAlias03.Service.Imp;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSeriveImp {
    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String fileName) throws  IOException;
}
