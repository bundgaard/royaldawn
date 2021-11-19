package org.tretton63.feikit.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.tretton63.feikit.filters.RequestIdFilter;
import org.tretton63.feikit.resolvers.RequestIdHandlerMethodArgumentResolver;

import java.util.List;

@Slf4j
@Configuration
public class RestConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilterFilterRegistrationBean() {
        log.info("Installing Request Id filter");
        FilterRegistrationBean<RequestIdFilter> mbean = new FilterRegistrationBean<>();
        mbean.setFilter(new RequestIdFilter());
        mbean.setOrder(5);
        return mbean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestIdHandlerMethodArgumentResolver());
    }

    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }
}
