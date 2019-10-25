package com.lhq.superboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhq.superboot.enums.ExpCode;

/**
 * @Description: 全局异常处理
 * 	在这里处理切面中无法处理的异常
 * @author: lihaoqi
 * @date: 2019年4月27日
 * @version: 1.0.0
 */
@ControllerAdvice
public class GobalExceptionHandle {

	@ExceptionHandler(value = Throwable.class)
	@ResponseBody
	public String handle(Throwable ex) {
		return ExceptionHandle.handle(ex, ExpCode.GOBALEXCEPTION.getCode());
	}
}
