package com.trekko.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trekko.api.interceptors.RateLimitFilter;

@Configuration
public class RateLimitConfig {

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
        final FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        final RateLimitFilter rateLimitFilter = new RateLimitFilter();

        registrationBean.setFilter(rateLimitFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
