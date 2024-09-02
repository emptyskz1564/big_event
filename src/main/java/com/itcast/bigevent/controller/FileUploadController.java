package com.itcast.bigevent.controller;

import com.itcast.bigevent.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {


    @PostMapping
    public Result<String> upload(MultipartFile multipartFile) throws IOException {
        //获取文件内容的输入流，写入到本地磁盘
        String originalFilename = multipartFile.getOriginalFilename();
        //为了防止同名文件覆盖问题，我们用UUID生成文件名
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid+originalFilename.substring(originalFilename.lastIndexOf("."));
        multipartFile.transferTo(new File("E:\\JavaLearn\\big-event\\src\\main\\resources\\file\\"+fileName));
        return Result.success("url访问地址:...");
    }

}
