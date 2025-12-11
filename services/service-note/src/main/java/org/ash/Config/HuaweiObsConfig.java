package org.ash.Config;


import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HuaweiObsConfig {

    @Value("${huawei.obs.access-key}")
    private String accessKey;

    @Value("${huawei.obs.secret-key}")
    private String secretKey;

    @Value("${huawei.obs.endpoint}")
    private String endpoint;

    @Bean
    public ObsClient obsClient() {
        // 创建ObsClient实例
        return new ObsClient(accessKey, secretKey, endpoint);
    }
}
