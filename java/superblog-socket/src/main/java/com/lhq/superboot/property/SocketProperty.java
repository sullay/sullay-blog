package com.lhq.superboot.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Description: 将socket配置文件读取
 *
 * @author: lihaoqi
 * @date: 2019年4月18日
 * @version: 1.0.0
 */
@Data
@Component
public class SocketProperty {

    @Value("${socketio.hostName}")
    private String hostName;
    
    @Value("${socketio.post}")
    private Integer post;

    @Value("${socketio.maxFramePayloadLength}")
    private Integer maxFramePayloadLength;
    
    @Value("${socketio.maxHttpContentLength}")
    private Integer maxHttpContentLength;
    
    @Value("${socketio.bossCount}")
    private int bossCount;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;
    
}

