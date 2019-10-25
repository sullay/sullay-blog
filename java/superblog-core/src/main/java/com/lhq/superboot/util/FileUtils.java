package com.lhq.superboot.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件工具类
 * @author: lct
 * @date: 2019年4月25日 下午1:29:04
 */
public class FileUtils {
	
	public static String saveFile(MultipartFile file, String fileName, String suffix, String savePath) throws IllegalStateException, IOException {
		fileName = changName(fileName, suffix);
		File desFile = new File(savePath, fileName);
		if(!desFile.getParentFile().exists()){
			desFile.mkdirs();
		}
		
		file.transferTo(desFile);
		return fileName;
	}
	
	private static String changName(String fileName, String suffix) {
		 
		String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		int rad = new Random().nextInt(900000000);
		String newName = fileName + "_" + time + "_" + rad + "." + suffix;
		System.out.println("文件新名称：" + newName);
		return newName;
	}
	
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
}
