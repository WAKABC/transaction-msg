package com.wak.transactionmsg.message.send;

import com.wak.transactionmsg.entities.MsgPO;

import java.util.Collections;
import java.util.List;

/**
 * @author wuankang
 * @date 2024/6/30 18:08
 * @Description TODO
 * @Version 1.0
 */
public interface IMsgSend {

    /**
     * 发送单条消息
     *
     * @param msg 消息
     */
    default void sendMsg(Object msg) {
        batchSend(Collections.singletonList(msg));
    }

    /**
     * 批量发送
     *
     * @param msgList 消息列表
     */
    void batchSend(List<Object> msgList);

    /**
     * 重试发送
     *
     * @param msg 消息
     */
    void retrySend(MsgPO msg);
}
