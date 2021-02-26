package club.mrxiao.spring.boot.starter.jdl.config;

import club.mrxiao.common.error.ExpressErrorException;
import club.mrxiao.jdl.api.JdlService;
import club.mrxiao.jdl.api.impl.JdlServiceImpl;
import club.mrxiao.jdl.config.JdlConfig;
import club.mrxiao.spring.boot.starter.jdl.properties.JdlExpressProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置.
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 * @since 2019-12-26
 */
@AllArgsConstructor
@Configuration
@ConditionalOnClass(JdlService.class)
@EnableConfigurationProperties(JdlExpressProperties.class)
@ConditionalOnProperty(prefix = "express.jdl", value = "enabled", matchIfMissing = true)
public class JdlExpressAutoConfiguration {

    private JdlExpressProperties properties;

    @Bean
    @ConditionalOnMissingBean(JdlService.class)
    public JdlService service() throws ExpressErrorException {
        JdlConfig config = new JdlConfig();
        config.setAppKey(properties.getAppKey());
        config.setAppSecret(properties.getAppSecret());
        config.setRefreshToken(properties.getRefreshToken());
        config.setCustomerCode(properties.getCustomerCode());
        JdlService service = new JdlServiceImpl();
        service.setConfig(config);
        return service;
    }
}