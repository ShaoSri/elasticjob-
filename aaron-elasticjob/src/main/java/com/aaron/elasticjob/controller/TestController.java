package com.aaron.elasticjob.controller;

import com.aaron.elasticjob.job.simple.MyElasticJob;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.simple.SimpleJobConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @ClassName: TestController
 * @Description :
 * @Author Aaron
 * @Date 2018/7/26
 * @Version 1.0
 */

@RestController
public class TestController {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;


    /**
     * 动态添加任务逻辑
     */
    @RequestMapping("/test")
    public void test() {
        int shardingTotalCount = 2;
        String jobName = UUID.randomUUID().toString() + "-test123";

        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                .newBuilder(jobName, "* * * * * ?", shardingTotalCount)
                .shardingItemParameters("0=A,1=B")
                .build();

        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(jobCoreConfiguration, MyElasticJob.class.getCanonicalName());

        JobScheduler jobScheduler = new JobScheduler(zookeeperRegistryCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).build());



        try {
            jobScheduler.init();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("定时任务创建失败");
        }
    }
}
