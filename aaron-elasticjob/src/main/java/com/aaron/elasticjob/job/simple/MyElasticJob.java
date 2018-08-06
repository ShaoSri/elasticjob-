package com.aaron.elasticjob.job.simple;

import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyElasticJob
 * @Description :
 * @Author Aaron
 * @Date 2018/7/26
 * @Version 1.0
 */
public class MyElasticJob implements SimpleJob {
    private final Logger logger = LoggerFactory.getLogger(MyElasticJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        //打印出任务相关信息，JobParameter用于传递任务的ID
        logger.info(String.format("------Thread ID: %s, 任务总片数: %s, " +
                        "当前分片项: %s,当前参数: %s," +
                        "当前任务名称: %s,当前任务参数: %s," +
                        "当前任务的id: %s",
                //获取当前线程的id
                Thread.currentThread().getId(),
                //获取任务总片数
                shardingContext.getShardingTotalCount(),
                //获取当前分片项
                shardingContext.getShardingItem(),
                //获取当前的参数
                shardingContext.getShardingParameter(),
                //获取当前的任务名称
                shardingContext.getJobName(),
                //获取当前任务参数
                shardingContext.getJobParameter(),
                //获取任务的id
                shardingContext.getTaskId()));
    }

}
