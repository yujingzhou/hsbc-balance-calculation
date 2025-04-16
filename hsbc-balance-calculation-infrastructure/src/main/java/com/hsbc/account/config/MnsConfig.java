package com.hsbc.account.config;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yubo
 * @Title: MnsConfig
 * @Package com.hsbc.account.config
 * @Description: 消息配置
 */
@Configuration
public class MnsConfig {
    @Value("${mns.endpoint}")
    private String endpoint;

    @Value("${mns.queueName}")
    private String queueName;

    @Value("${mns.accessKeyId}")
    private String accessKeyId;

    @Bean
    public MNSClient getFaceMergeClient() throws Exception {
        CloudAccount account = new CloudAccount(accessKeyId, System.getenv("mns.accessKeySecret"), endpoint);
        MNSClient mnsClient = account.getMNSClient();
        return mnsClient;
    }
}
