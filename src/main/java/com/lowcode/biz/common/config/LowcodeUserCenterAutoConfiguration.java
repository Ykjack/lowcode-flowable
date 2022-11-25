package com.lowcode.biz.common.config;

import com.lowcode.biz.service.impl.LowocdeIdentityServiceImpl;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.FlowableSecurityAutoConfiguration;
import org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration;
import org.flowable.spring.boot.idm.IdmEngineServicesAutoConfiguration;
import org.flowable.spring.security.FlowableAuthenticationProvider;
import org.flowable.ui.common.properties.FlowableCommonAppProperties;
import org.flowable.ui.common.security.CustomPersistentRememberMeServices;
import org.flowable.ui.common.security.FlowableUiSecurityAutoConfiguration;
import org.flowable.ui.common.security.PersistentTokenService;
import org.flowable.ui.idm.conf.IdmSecurityConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({
        FlowableUiSecurityAutoConfiguration.class,
        FlowableSecurityAutoConfiguration.class,
        IdmEngineServicesAutoConfiguration.class,
        ProcessEngineServicesAutoConfiguration.class,
        IdmSecurityConfiguration.class
})
public class LowcodeUserCenterAutoConfiguration implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public LowcodeUserCenterAutoConfiguration() {

    }

    @Bean
    public EngineConfigurationConfigurer<SpringIdmEngineConfiguration> xmwIdmEngineConfigurer() {
        return idmEngineConfiguration -> idmEngineConfiguration
                .setIdmIdentityService(new LowocdeIdentityServiceImpl(applicationContext, idmEngineConfiguration));
    }

    @Bean
//    @ConditionalOnMissingBean(AuthenticationProvider.class)
    public FlowableAuthenticationProvider flowableAuthenticationProvider(IdmIdentityService idmIdentitySerivce,
                                                                         @Qualifier("lowcodeUserDetailsServiceImpl") UserDetailsService userDetailsService) {
        return new FlowableAuthenticationProvider(idmIdentitySerivce, userDetailsService);
    }

    @Bean
    @ConditionalOnMissingBean
    public RememberMeServices flowableUiRememberMeService(FlowableCommonAppProperties properties, @Qualifier("lowcodeUserDetailsServiceImpl") UserDetailsService userDetailsService,
                                                          PersistentTokenService persistentTokenService) {
        return new CustomPersistentRememberMeServices(properties, userDetailsService, persistentTokenService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
