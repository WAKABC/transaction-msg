package com.wak.transactionmsg.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 本地消息表
 * @TableName t_msg
 */
@TableName(value ="t_msg")
@Data
public class MsgPO implements Serializable {
    /**
     * 消息id
     */
    @TableId
    private String id;

    /**
     * 消息体,json格式
     */
    private String bodyJson;

    /**
     * 消息状态，0：待投递到mq，1：投递成功，2：投递失败
     */
    private Integer status;

    /**
     * status=2 时，记录消息投递失败的原因
     */
    private String failMsg;

    /**
     * 已投递失败次数
     */
    private Integer failCount;

    /**
     * 投递MQ失败了，是否还需要重试？1：是，0：否
     */
    private Integer sendRetry;

    /**
     * 投递失败后，下次重试时间
     */
    private LocalDateTime nextRetryTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最近更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MsgPO other = (MsgPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBodyJson() == null ? other.getBodyJson() == null : this.getBodyJson().equals(other.getBodyJson()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getFailMsg() == null ? other.getFailMsg() == null : this.getFailMsg().equals(other.getFailMsg()))
            && (this.getFailCount() == null ? other.getFailCount() == null : this.getFailCount().equals(other.getFailCount()))
            && (this.getSendRetry() == null ? other.getSendRetry() == null : this.getSendRetry().equals(other.getSendRetry()))
            && (this.getNextRetryTime() == null ? other.getNextRetryTime() == null : this.getNextRetryTime().equals(other.getNextRetryTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBodyJson() == null) ? 0 : getBodyJson().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getFailMsg() == null) ? 0 : getFailMsg().hashCode());
        result = prime * result + ((getFailCount() == null) ? 0 : getFailCount().hashCode());
        result = prime * result + ((getSendRetry() == null) ? 0 : getSendRetry().hashCode());
        result = prime * result + ((getNextRetryTime() == null) ? 0 : getNextRetryTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bodyJson=").append(bodyJson);
        sb.append(", status=").append(status);
        sb.append(", failMsg=").append(failMsg);
        sb.append(", failCount=").append(failCount);
        sb.append(", sendRetry=").append(sendRetry);
        sb.append(", nextRetryTime=").append(nextRetryTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}