package com.wak.transactionmsg.service;

import com.wak.transactionmsg.entities.MsgPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author wuankang
* @description 针对表【t_msg(本地消息表)】的数据库操作Service
* @createDate 2024-06-30 18:00:09
*/
public interface IMsgService extends IService<MsgPO> {

    /**
     * 批量插入
     *
     * @param msgBodyJsonList json格式消息体
     * @return int
     */
    List<MsgPO> batchInsert(List<String> msgBodyJsonList);

    /**
     * 更新状态成功
     *
     * @param msgPO 消息
     */
    void updateStatusSuccess(MsgPO msgPO);

    /**
     * 更新状态失败
     *
     * @param msgPO         消息
     * @param reason        原因
     * @param retry         是否重试
     * @param nextRetryTime 下次重试时间
     */
    void updateStatusFail(MsgPO msgPO, String reason, boolean retry, LocalDateTime nextRetryTime);
}
