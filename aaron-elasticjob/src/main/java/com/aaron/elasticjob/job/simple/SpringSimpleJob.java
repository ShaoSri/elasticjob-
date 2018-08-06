/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.aaron.elasticjob.job.simple;



import com.aaron.elasticjob.fixture.entity.Foo;
import com.aaron.elasticjob.fixture.repository.FooRepository;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpringSimpleJob implements SimpleJob {
    private final Logger logger = LoggerFactory.getLogger(SpringSimpleJob.class);

    @Resource
    private FooRepository fooRepository;
    
    @Override
    public void execute(final ShardingContext shardingContext) {

        logger.info(String.format("------Thread ID: %s, 任务总片数: %s, " +
                        "当前分片项: %s,当前参数: %s," +
                        "当前任务名称: %s,当前任务参数: %s,"+
                        "当前任务的id: %s"
                ,
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

        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);

        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
