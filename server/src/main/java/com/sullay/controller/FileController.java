package com.sullay.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sullay.model.Msg;


@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {
	@Value("${filePath}")
    private String filePath;
	@PostMapping("/uploadFile")
	public Msg multiUpload(HttpServletRequest request) {
		File fileP = new File(filePath);
		Msg msg = Msg.success();
		if(!fileP.exists()){
			fileP.mkdir();
		} 
	    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	    for (int i = 0; i < files.size(); i++) {
	        MultipartFile file = files.get(i);
	        if (file.isEmpty()) {
	            return Msg.fail();
	        }
	        File dest = null;
	        while(dest==null||dest.exists()) {
		        String fileName = file.getOriginalFilename();
		        msg.add("fileName", fileName);
		        String uuid= UUID.randomUUID().toString();
		        msg.add("value", uuid+fileName);
		        dest = new File(filePath+uuid+fileName);
	        }
	        try {
	            file.transferTo(dest);
	        } catch (IOException e) {
	            return Msg.fail();
	        }
	    }

	    return msg;

	}
}
