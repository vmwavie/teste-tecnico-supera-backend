package com.vmwavie.backend.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    private final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIp = httpRequest.getRemoteAddr();

        long currentTime = System.currentTimeMillis();
        requestCounts.entrySet().removeIf(entry -> currentTime - entry.getValue() > TIME_WINDOW);

        long requestCount = requestCounts.getOrDefault(clientIp, 0L);
        long RATE_LIMIT = 100;
        if (requestCount >= RATE_LIMIT) {
            response.getWriter().write("Rate limit exceeded");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            return;
        }

        requestCounts.put(clientIp, requestCount + 1);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}