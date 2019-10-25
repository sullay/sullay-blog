package com.lhq.superboot.util;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;

/**
 * @Description: 分页工具类
 *
 * @author: lihaoqi 
 * @date: 2019年8月15日 下午3:39:56 
 * @version: v1.0.0
 */
@Component
public class PagesUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);
    
    /** 每页最大显示量 **/
    private static Integer MAXSIZE = 200;
    
    @Value("${page.maxSize}")
    public void setMaxSize(Integer maxSize) {
        MAXSIZE = maxSize;
    }
    
    /**
     * @Description: 检查分页大小
     *
     * @param page
     * @return
     */
    public static PageInfo<T> checkPageSize(PageInfo<T> page) {
        int pageSize = page.getPageSize();
        int pageNum = page.getPageNum();
        
        if (pageNum < 0) {
            page.setPageNum(0);
        }
        if (pageSize < 0) {
            page.setPageSize(0);
        }
        if (pageSize > MAXSIZE) {
            logger.warn("设置页面数量{}过大，将调整为{}，可能丢失数据", pageSize, MAXSIZE);
            page.setPageSize(MAXSIZE);
        }
        return page;
    }
}
