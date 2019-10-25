package com.lhq.superboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.lhq.superboot.property.SocketProperty;

/**
 * @Description: netty-socketio工具类 创建、添加和启动客户端 消息推送 关闭服务
 *
 * @author: lihaoqi
 * @date: 2019年8月29日 下午3:58:58
 * @version: v1.0.0
 */
@Configuration
public class SocketioConfig {

    
    @Autowired
    private SocketProperty socketProperty;
    
    @Bean
    public SocketIOServer socketioServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(socketProperty.getHostName());
        config.setPort(socketProperty.getPost());
        config.setBossThreads(socketProperty.getBossCount());
        config.setWorkerThreads(socketProperty.getWorkCount());
        config.setAllowCustomRequests(socketProperty.isAllowCustomRequests());
        config.setUpgradeTimeout(socketProperty.getUpgradeTimeout());
        config.setPingTimeout(socketProperty.getPingTimeout());
        config.setPingInterval(socketProperty.getPingInterval());
        return new SocketIOServer(config);
    }

}