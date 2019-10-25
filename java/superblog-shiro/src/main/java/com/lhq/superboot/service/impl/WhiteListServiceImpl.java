package com.lhq.superboot.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhq.superboot.domain.WhiteListUrl;
import com.lhq.superboot.domain.WhiteListUrlExample;
import com.lhq.superboot.mapper.WhiteListUrlMapper;
import com.lhq.superboot.service.WhiteListService;

/**
 * @Description: 白名单相关查询
 *
 * @author: lihaoqi 
 * @date: 2019年8月12日 下午11:46:16 
 * @version: v1.0.0
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {
	
	private static final Logger logger = LoggerFactory.getLogger(WhiteListService.class);
	
	@Autowired
	private WhiteListUrlMapper whiteMapper;

	@Override
	public List<WhiteListUrl> selectWhiteListUrl() {
		List<WhiteListUrl> whiteList = whiteMapper.selectByExample(new WhiteListUrlExample());
		
		logger.debug("白名单列表：{}", whiteList);
		return whiteList;
	}

}
