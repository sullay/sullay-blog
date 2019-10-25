package com.lhq.superboot.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.lhq.superboot.domain.PushMessage;
import com.lhq.superboot.enums.EventEnum;
import com.lhq.superboot.service.SocketIOService;
import com.lhq.superboot.util.StringUtils;

/**
 * @Description: socket接口实现类
 *
 * @author: lihaoqi
 * @date: 2019年8月29日 下午6:46:52
 * @version: v1.0.0
 */
@Service
public class SocketIOServiceImpl implements SocketIOService {
    
    private static final Logger logger = LoggerFactory.getLogger(SocketIOService.class);

    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * @Description: Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * 
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * @Description: Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * 
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        stop();
    }

    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String user = getParamsByClient(client);
            if (user != null) {
                clientMap.put(user, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String user = getParamsByClient(client);
            if (user != null) {
                clientMap.remove(user);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        for (EventEnum eventEnum : EventEnum.values()) {
            socketIOServer.addEventListener(eventEnum.getCode(), PushMessage.class, (client, data, ackSender) -> {
                logger.debug(data.getContent());
            });
        }

        socketIOServer.start();
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToUser(PushMessage pushMessage) {
        String userId = pushMessage.getUserId();
        String event = pushMessage.getEvent();
        if (StringUtils.isNotEmpty(userId)) {
            SocketIOClient client = clientMap.get(userId);
            if (client != null)
                client.sendEvent(event, pushMessage.getContent());
        }
    }

    /**
     * @Description: 此方法为获取client连接中的参数，可根据需求更改
     * 
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的userId必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("userId");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
