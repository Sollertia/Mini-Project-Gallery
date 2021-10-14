package com.hanghae.gallery.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class ImgStore {

    @Value("${file.dir}") // application.properties 에 설정한 이미지저장소의 Base 경로
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    // 설정한 경로에 이미지 파일 저장하기
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storedFileName = createStoredFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storedFileName)));
        return new UploadFile(originalFilename, storedFileName);
    }
    // 저장하는 파일 이름을 만들어준다 - 중복되면 덮어씌어지기 때문
    private String createStoredFileName(String originalFilename) {

        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;

    }
    // 업로드된 파일의 확장자명을 분리해 저장할 파일에 붙여준다
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


}
