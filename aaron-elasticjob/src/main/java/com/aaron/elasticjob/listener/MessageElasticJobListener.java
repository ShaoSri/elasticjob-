package com.aaron.elasticjob.listener;

import io.elasticjob.lite.api.listener.ElasticJobListener;
import io.elasticjob.lite.executor.ShardingContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地监听器
 * @ClassName: MyElasticJobListener
 * @Description  监听器
 * @Author Aaron
 * @Date 2018/7/25
 * @Version 1.0
 */

public class MessageElasticJobListener implements ElasticJobListener{

    private final Logger logger = LoggerFactory.getLogger(MessageElasticJobListener.class);

    /**
     * 任务开始
     * @param shardingContexts
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        logger.info("=====================本地监听器任务开始");
    }

    /**
     * 任务结束
     * @param shardingContexts
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        logger.info("=====================本地监听器任务结束");
    }

}