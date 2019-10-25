package com.lhq.superboot.service;

import com.lhq.superboot.domain.PushMessage;

/**
 * @Description:
 *
 * @author: lihaoqi
 * @date: 2019年8月29日 下午6:43:13
 * @version: v1.0.0
 */
public interface SocketIOService {
    
    // 启动服务
    public void start() throws Exception;

    // 停止服务
    public void stop();

    // 推送信息
    public void pushMessageToUser(PushMessage pushMessage);
}
