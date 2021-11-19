package org.tretton63.feikit.converters;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tretton63.feikit.filters.RequestIdFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestIdConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return getOrEmptyRequestId().orElse("");
    }

    static Optional<String> getOrEmptyRequestId() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String requestId = request.getHeader(RequestIdFilter.REQUEST_HEADER);
            return Optional.of(requestId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
