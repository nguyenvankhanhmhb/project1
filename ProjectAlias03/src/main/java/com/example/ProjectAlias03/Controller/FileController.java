package com.example.ProjectAlias03.Controller;

import com.example.ProjectAlias03.Service.Imp.FileSeriveImp;
import com.example.ProjectAlias03.payload.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileSeriveImp fileSeriveImp;


    @PostMapping("")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String message = fileSeriveImp.uploadFile(file);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("thanh cong");
        baseResponse.setData(message);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {

        byte[] fileImage = fileSeriveImp.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(fileImage, headers, HttpStatus.OK );
    }
}
