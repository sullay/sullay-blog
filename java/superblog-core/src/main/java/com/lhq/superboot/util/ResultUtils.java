package com.lhq.superboot.util;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lhq.superboot.domain.Result;
import com.lhq.superboot.enums.ResultMsg;

/**
 * @Description: 异常结果处理
 *
 * @author: lihaoqi
 * @date: 2019年4月13日
 * @version: v1.0.0
 */
public class ResultUtils {

    /** 登录返回sessionId **/
    public static String successWithSessionId(String msg, Serializable sessionId) {
        Result<Object> result = new Result<Object>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(msg);
        result.setSessionId((String) sessionId);
        return FastJsonUtils.toJSONFeatures(result);
    }

    /** 有参数有信息成功调用 **/
    public static String success(Object object, String msg) {
        Result<Object> result = new Result<Object>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(msg);
        result.setData(object);
        result.setSessionId(getSessionId());
        return FastJsonUtils.toJSONFeatures(result);
    }

    /** 无参数无信息成功调用 **/
    public static String success() {
        return success(null, ResultMsg.SUCCESS.getCode());
    }

    /** 无参数有信息成功调用 **/
    public static String success(String msg) {
        return success(null, msg);
    }

    /** 有参数无信息成功调用 **/
    public static String success(Object object) {
        return success(object, ResultMsg.SUCCESS.getCode());
    }

    /** 有堆栈信息有状态有信息失败调用 **/
    public static String error(Integer code, String msg, Throwable e) {
        Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSessionId(getSessionId());
        if (e != null) {
            result.setStack(ExceptionUtils.getException(e).toString());
        }
        return FastJsonUtils.toJSONFeatures(result);
    }

    /** 无状态无信息失败调用 **/
    public static String error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResultMsg.FAIL.getCode(), null);
    }

    /** 有状态有信息调用失败 **/
    public static String error(Integer code, String msg) {
        return error(code, msg, null);
    }

    /** 无状态有信息调用失败 **/
    public static String error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    /** 有状态无信息调用失败 **/
    public static String error(Integer code) {
        return error(code, ResultMsg.FAIL.getCode(), null);
    }

    /** 无状态无信息失败调用 **/
    public static String error(Throwable e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResultMsg.FAIL.getCode(), e);
    }

    private static String getSessionId() {
        String sessionId = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return sessionId;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("SHIROJSESSIONID")) {
                    sessionId = cookie.getValue();
                }
            }
        }
        return sessionId;
    }

}
