package com.aaron.elasticjob.listener;

import io.elasticjob.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import io.elasticjob.lite.executor.ShardingContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式监听器
 * @ClassName: MyDistributedListener
 * @Description :
 * @Author Aaron
 * @Date 2018/7/30
 * @Version 1.0
 */

public class MyDistributedListener extends AbstractDistributeOnceElasticJobListener {
    private final Logger logger = LoggerFactory.getLogger(MyDistributedListener.class);



    public MyDistributedListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        logger.info("=====================分布式监听器任务开始");
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        logger.info("=====================分布式监听器任务结束");
    }
}
