package com.example.ProjectAlias03.payload;

import org.springframework.web.multipart.MultipartFile;

public class BaseResponse {


    //lỗi 415
//    private String name;
//    private String url;
//    private MultipartFile data;
//




    private int statusCode = 200;
    private String message = "";
    private Object data = "";

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
