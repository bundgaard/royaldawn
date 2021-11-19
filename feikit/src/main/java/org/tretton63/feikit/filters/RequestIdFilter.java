package org.tretton63.feikit.filters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RequestIdFilter implements Filter {
    public static final String REQUEST_HEADER = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest &&
                response instanceof HttpServletResponse httpResponse
        ) {
            RequestId requestId = getOrCreateRequestId(httpRequest);
            RequestWrapper wrapper = new RequestWrapper(httpRequest);
            wrapper.addHeader(REQUEST_HEADER, requestId.getRequestId());
            httpResponse.setHeader(REQUEST_HEADER, requestId.getRequestId());
            log.debug("filter setting requestId on request and response {}", requestId.getRequestId());
            chain.doFilter(wrapper, httpResponse);
        } else {
            chain.doFilter(request, response);
        }
    }

    static class RequestWrapper extends HttpServletRequestWrapper {
        private final MultiValueMap<String, String> values = new LinkedMultiValueMap<>();

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            if (value != null && values.containsKey(value)) {
                value = values.getFirst(name);
            }
            return value;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> keys = Collections.list(super.getHeaderNames());
            values.forEach((k, v) -> keys.add(k));
            return Collections.enumeration(keys);
        }

        public void addHeader(String requestHeader, String requestId) {
            values.add(requestHeader, requestId);
        }
    }

    RequestId getOrCreateRequestId(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .filter(
                        headerKey ->
                                headerKey.equalsIgnoreCase(REQUEST_HEADER)
                ).findFirst()
                .map(key -> RequestId.builder()
                        .key(key)
                        .requestId(request.getHeader(key))
                        .build()
                ).orElse(RequestId.builder()
                        .key(REQUEST_HEADER)
                        .requestId(UUID.randomUUID().toString())
                        .build()
                );
    }

    @Getter
    @Setter
    @Builder
    public static class RequestId {
        String key;
        String requestId;

        public static RequestId from(String value) {
            return RequestId.builder().requestId(value).key(REQUEST_HEADER).build();
        }

        public static RequestId create() {
            return RequestId.builder().key(REQUEST_HEADER).requestId(UUID.randomUUID().toString()).build();
        }

        public String toString() {
            return requestId;
        }
    }
}
