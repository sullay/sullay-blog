package com.lhq.superboot.config;

import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.lhq.superboot.enums.ExpCode;
import com.lhq.superboot.exception.ExceptionHandle;
import com.lhq.superboot.util.PagesUtils;
import com.lhq.superboot.util.ResultUtils;

/**
 * @Description: 访问拦截器
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 *
 */
@Component
@Aspect
public class AopConfig {

	private static final Logger logger = LoggerFactory.getLogger(AopConfig.class);
	
	/** "within(com.lhq.boot..*)" 包及其子包下的所有类方法 **/	
	/**private final String POINT_CUT = "execution(public * com.lhq.superboot.controller.*.*(..))"; **/
	private final String POINT_CUT = "within(com.lhq.superboot.controller..*) "
			+ "&& !execution(public * com.lhq.superboot.controller.login.*.*(..))";

	@Pointcut(POINT_CUT)
	public void pointCut() {
	}

	/**
	 * @Description: 环绕通知： 注意:Spring AOP的环绕通知会影响到AfterThrowing通知的运行,不要同时使用
	 *
	 * @param proceedingJoinPoint
	 * @return
	 */
	@Around(value = POINT_CUT)
	public String doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		logger.debug("︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻▶▷►◈华丽的分割线◈◄◀◁︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻︻");
		logger.debug("方法执行开始：" + proceedingJoinPoint.getSignature().toString());
		Object obj = null;
		Object[] paramObjArr = proceedingJoinPoint.getArgs();
		
		try {
			checkParam(paramObjArr);
		    obj = proceedingJoinPoint.proceed(paramObjArr);
			logger.debug("方法正常执行结束，返回值：" + obj);
			return ResultUtils.success(obj);
		} catch (Throwable ex) {
			return ExceptionHandle.handle(ex, ExpCode.AOPEXCEPTION.getCode());
		} finally {
			logger.debug("︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼▶▷►◈华丽的分割线◈◄◀◁︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼︼\n");
		}
	}
	
	/**
     * @Description: 执行方法前，检查参数
     *
     * @param paramObjArr
     */
    @SuppressWarnings("unchecked")
    private void checkParam(Object[] paramObjArr) {
        for (int i = 0; i < paramObjArr.length; i++) {
            // 检查分页大小
            if (paramObjArr[i] instanceof PageInfo) {
                paramObjArr[i] = PagesUtils.checkPageSize((PageInfo<T>) paramObjArr[i]);
            }
        }
    }
}
