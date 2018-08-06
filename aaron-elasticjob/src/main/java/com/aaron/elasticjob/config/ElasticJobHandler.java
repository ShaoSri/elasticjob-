package com.aaron.elasticjob.config;

import com.aaron.elasticjob.job.simple.MyElasticJob;
import com.aaron.elasticjob.listener.MessageElasticJobListener;
import io.elasticjob.lite.api.simple.SimpleJob;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.simple.SimpleJobConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ElasticJobHandler
 * @Description :
 * @Author Aaron
 * @Date 2018/7/26
 * @Version 1.0
 */

@Component
public class ElasticJobHandler {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;


    @Autowired
    private MessageElasticJobListener messageElasticJobListener;

    /**
     * @param jobName
     * @param jobClass
     * @param shardingTotalCount
     * @param cron
     * @param id                 数据ID
     * @return
     */
    private static LiteJobConfiguration.Builder simpleJobConfigBuilder(String jobName,
                                                                       Class<? extends SimpleJob> jobClass,
                                                                       int shardingTotalCount,
                                                                       String cron,
                                                                       String id,String parameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration
                        .newBuilder(jobName, cron, shardingTotalCount)
                        .shardingItemParameters(parameters)
                        .jobParameter(id).
                        build(),
                jobClass.getCanonicalName()));
    }

    /**
     * 添加一个定时任务
     * @param jobName            任务名
     * @param cron               表达式
     * @param shardingTotalCount 分片数
     * @param parameters         当前参数
     */
    public void addJob(String jobName, String cron, Integer shardingTotalCount, String id,String parameters) {
        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, MyElasticJob.class, shardingTotalCount, cron, id,parameters)
                .overwrite(true).build();

     }
}