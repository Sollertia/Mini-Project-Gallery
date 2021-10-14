package com.hanghae.gallery.util;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; // 업로드된 실제 파일 이름

    private String storedFileName; // 저장소에 저장되는 이름

    public UploadFile(String uploadFileName, String storedFileName){
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
    }


}
