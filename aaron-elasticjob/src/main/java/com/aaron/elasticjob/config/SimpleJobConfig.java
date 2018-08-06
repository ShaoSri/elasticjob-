
package com.aaron.elasticjob.config;


import com.aaron.elasticjob.job.simple.SpringSimpleJob;
import com.aaron.elasticjob.listener.MessageElasticJobListener;
import com.aaron.elasticjob.listener.MyDistributedListener;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.api.simple.SimpleJob;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.simple.SimpleJobConfiguration;
import io.elasticjob.lite.event.JobEventConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import io.elasticjob.lite.spring.api.SpringJobScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SimpleJobConfig {
    
    @Resource
    private ZookeeperRegistryCenter regCenter;
    
    @Resource
    private JobEventConfiguration jobEventConfiguration;
    
    @Bean
    public SimpleJob simpleJob() {
        return new SpringSimpleJob();
    }

    /**
      * 本地监听器
      * @author: Aaron
      */
    @Bean
    public  MessageElasticJobListener messageElasticJobListener(){
        return new MessageElasticJobListener();
    }

    /**
     * 分布式监听超时参数
     */
    @Value("${distributedlistener.startedTimeoutMillisecond}")
    private long startedTimeoutMillisecond;

    /**
     * 分布式监听超时参数
     */
    @Value("${distributedlistener.completedTimeoutMilliseconds}")
    private long completedTimeoutMilliseconds;

    /**
     * 分布式监听器
     * @author: Aaron
     */
    @Bean
    public MyDistributedListener myDistributedListener(){
        return new MyDistributedListener(startedTimeoutMillisecond,completedTimeoutMilliseconds);
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob, @Value("${simpleJob.cron}") final String cron, @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration, messageElasticJobListener(),myDistributedListener());
    }

    /**
     *  作业配置
     *  作业配置分为3级，分别是JobCoreConfiguration，JobTypeConfiguration和LiteJobConfiguration。
     *  LiteJobConfiguration使用JobTypeConfiguration，JobTypeConfiguration使用JobCoreConfiguration，层层嵌套。
     *  JobTypeConfiguration根据不同实现类型分为SimpleJobConfiguration，DataflowJobConfiguration和ScriptJobConfiguration。
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }




}
