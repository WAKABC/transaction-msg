package com.wak.transactionmsg.message.retry;

import com.wak.transactionmsg.entities.MsgPO;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wuankang
 * @date 2024/7/1 14:20
 * @Description TODO
 * @Version 1.0
 */
@Component
public class DefaultMqSendRetry implements MqSendRetry {
    //延迟规则
    private static final Map<Range<Integer>, DelayLevelEnums> retryDelayLevelMap = new LinkedHashMap<>();
    //最大重试次数
    private static final Integer MAX_RETRY_COUNT = 50;

    static {
        retryDelayLevelMap.put(Range.between(0, 5), DelayLevelEnums.SECOND_10);
        retryDelayLevelMap.put(Range.between(6, 10), DelayLevelEnums.SECOND_30);
        retryDelayLevelMap.put(Range.between(11, 15), DelayLevelEnums.MINUTE_1);
        retryDelayLevelMap.put(Range.between(16, 20), DelayLevelEnums.MINUTE_5);
        retryDelayLevelMap.put(Range.between(21, 50), DelayLevelEnums.MINUTE_10);
    }

    @Override
    public MqSendRetryResult getMqSendRetryResult(MsgPO msgPO) {
        //创建返回对象
        MqSendRetryResult result = new MqSendRetryResult();
        //获取下第一次重试时间
        Integer failCount = msgPO.getFailCount();
        //判断是否超过最大重试次数
        if (failCount < MAX_RETRY_COUNT) {
            LocalDateTime nextRetryTime = getNextRetryTime(failCount);
            result.setRetry(true);
            result.setNextRetryTime(nextRetryTime);
        }
        return result;
    }

    /**
     * 获取下一次重试时间
     *
     * @param failCount 失败数
     */
    private static LocalDateTime getNextRetryTime(Integer failCount) {
        for (Range<Integer> range : retryDelayLevelMap.keySet()) {
            if (range.contains(failCount)) {
                DelayLevelEnums delayLevelEnums = retryDelayLevelMap.get(range);
                long delayTimeInMills = delayLevelEnums.getDelayTimeInMills();
                //当前时间加上时间间隔
                return LocalDateTime.now().plus(delayTimeInMills, ChronoUnit.MILLIS);
            }
        }
        //没有匹配到，一小时后重试
        return LocalDateTime.now().plusHours(DelayLevelEnums.HOUR_1.getDelayTimeInMills());
    }
}
