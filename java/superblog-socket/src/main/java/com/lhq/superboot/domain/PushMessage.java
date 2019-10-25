package com.lhq.superboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: 推送到客户端的消息体
 *
 * @author: lihaoqi 
 * @date: 2019年8月29日 下午6:45:31 
 * @version: v1.0.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    
    private String userId;

    private String event;

    private String content;

    // Other Detail Property...
}