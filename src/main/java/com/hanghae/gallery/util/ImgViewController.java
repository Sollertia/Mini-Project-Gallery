package com.hanghae.gallery.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class ImgViewController {

    private final ImgStore imgStore;

    @GetMapping("/img/{filename}")
    @ResponseBody
    public Resource imgView(@PathVariable String filename) throws MalformedURLException {
        // UrlResource 프로토콜 꼭 포함해서 어떤 형태의 url 자료인지를 명시해줘야한다.
        return new UrlResource("file",imgStore.getFullPath(filename));
    }
}
