package org.tretton63.feikit.clients.interceptors;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tretton63.feikit.filters.RequestIdFilter;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestIdInterceptor {

    @Bean
    public RequestInterceptor interceptor() {
        log.info("Installing Feign interceptor");
        return requestTemplate -> {
            try {
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String requestId = request.getHeader(RequestIdFilter.REQUEST_HEADER);
                if (requestId == null || requestId.length() < 1){
                    log.info("HttpServletRequest Request Id is empty");
                } else {
                    log.info("Interceptor Request Id {}", requestId);
                    requestTemplate.header(RequestIdFilter.REQUEST_HEADER, requestId);
                }

            } catch (Exception e) {
                log.info("interceptor error {}", e.getMessage());
            }

        };
    }
}
