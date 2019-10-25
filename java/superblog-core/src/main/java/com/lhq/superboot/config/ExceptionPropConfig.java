package com.lhq.superboot.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.lhq.superboot.util.RedisUtils;

/**
 * @Description: 异常配置,初始化异常到缓存
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
@Component
@Configuration
public class ExceptionPropConfig {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionPropConfig.class);
	
	private final ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

	@Value("${exception.classpath}")
	private String expClassPath;

	@Value("${exception.language}")
	private String expLanguage;
	
	@Value("${exception.namespace}")
	private String expNamespace;
	
	@javax.annotation.Resource
	private RedisUtils redisUtils;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		String location = ResourceUtils.CLASSPATH_URL_PREFIX + expClassPath;
		PropertySourceLoader loader = new PropertiesPropertySourceLoader();
		Map<String, Object> expPropMap = new HashMap<String, Object>(1024);

		try {
			logger.debug("[ExceptionPropConfig] -> 加载异常配置文件");
			Resource[] resources = this.resourceLoader.getResources(location);
			for (Resource resource : resources) {
				String filename = resource.getFilename();
				if (!filename.contains(expLanguage)) {
					continue;
				}
				List<PropertySource<?>> propertySources = loader.load(filename, resource);
				if (null == propertySources || propertySources.isEmpty()) {
					continue;
				}

				for (PropertySource<?> propertySource : propertySources) {
					Map<String, Object> properties = (Map<String, Object>) propertySource.getSource();
					checkRepeatProp(expPropMap, properties, filename);
					expPropMap.putAll(properties);
				}
			}
			storeInRedis(expPropMap);
		} catch (IOException e) {
			logger.error("加载异常配置文件出错, {}", e.getMessage(), e);
		} catch (ClassCastException e) {
			logger.error("类型转化异常, {}", e.getMessage(), e);
		}
	}

	/**
	 * @Description: 异常配置存入redis缓存
	 * 
	 * @param expPropMap
	 */
	private void storeInRedis(Map<String, Object> expPropMap) {
		logger.debug("[ExceptionPropConfig] -> 加载异常进入redis缓存");
		if (expPropMap.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Object> entry : expPropMap.entrySet()) {
			if (null != entry.getKey() && null != entry.getValue()) {
				redisUtils.set(expNamespace + entry.getKey(), entry.getValue().toString());
			}
		}
	}

	/**
	 * @Description: 检查异常文件中是否存在相同key值
	 * 
	 * @param expPropMap
	 * @param properties
	 * @param filename
	 */
	private void checkRepeatProp(Map<String, Object> expPropMap, Map<String, Object> properties, String filename) {
		Set<String> expPropSet = expPropMap.keySet();
		Iterator<String> itor = expPropSet.iterator();
		while (itor.hasNext()) {
			Object obj = itor.next();
			if (properties.containsKey(obj)) {
				logger.warn("异常配置文件中有重复key值在" + filename + "文件中，重复值为：" + obj);
			}
		}
	}

}