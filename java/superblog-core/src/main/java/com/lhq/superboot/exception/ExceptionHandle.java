package com.lhq.superboot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.lhq.superboot.util.ResultUtils;

/**
 * @Description: 切面异常处理
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class ExceptionHandle {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	
	public static String handle(Throwable ex, String source) {
		logger.debug("[{}Exception] 异常捕获", source);
		String msg = null;

		while (ex != null) {
			if (ex instanceof SuperBootException) {
				msg = ((SuperBootException) ex).getMessage();
				logger.error(msg);
				break;
			}
			Throwable tex = ex.getCause();
			if (tex == null) {
				msg = ex.getMessage();
				break;
			} else {
				ex = tex;
			}
		}
		if (msg == null || msg.trim().length() <= 0) {
			msg = ex.getMessage();
			logger.error("服务接口出错", msg);
		}
		logger.debug("[{}Exception] 异常信息：{}", source, msg);
		return ResultUtils.error(HttpStatus.BAD_REQUEST.value(), msg);
	}

}
