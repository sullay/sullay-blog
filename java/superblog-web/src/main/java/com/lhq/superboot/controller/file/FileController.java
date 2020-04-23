package com.lhq.superboot.controller.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Value("${filePath}")
	private String filePath;

	@PostMapping("/uploadFile")
	public Object multiUpload(HttpServletRequest request) throws Exception {
		File fileP = new File(filePath);
		Map<String, Object> result = new HashMap<>();

		if (!fileP.exists()) {
			fileP.mkdir();
		}
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			if (file.isEmpty()) {
				throw new Exception("文件为空");
			}
			File dest = null;
			while (dest == null || dest.exists()) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isEmpty(fileName)) {
					fileName = "无标题";
				}
				result.put("fileName", fileName);
				String uuid = UUID.randomUUID().toString();
				result.put("value", uuid + URLEncoder.encode(fileName, Charset.forName(StandardCharsets.UTF_8.name())).replaceAll("\\+", "%20"));
				dest = new File(filePath + uuid + fileName);
			}
			try {
				file.transferTo(dest);
			} catch (IOException e) {
				throw new Exception("异常信息：" + e.getMessage());
			}
		}

		return result;

	}

}
