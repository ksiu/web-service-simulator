package com.kensoft.test.ws.simulator;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public final class LoggingFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        chain.doFilter(request, response);

        logRequest(httpRequest);
    }

    private void logRequest(HttpServletRequest httpRequest) {
        StringBuilder builder = new StringBuilder("CLIENT REQUEST: ");
        builder.append("URL=" + httpRequest.getRequestURI());
        builder.append(", parameters=[");
        Map parameterMap = httpRequest.getParameterMap();
        if (parameterMap != null) {
            for (Object item : parameterMap.entrySet()) {
                Map.Entry entry = (Map.Entry) item;
                builder.append(entry.getKey() + "='" + entry.getValue() + "'; ");
            }
        }
        builder.append("], headers=[");
        Enumeration headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                builder.append(headerName + "='" + httpRequest.getHeader(headerName) + "'; ");
            }
        }
        builder.append("]");
        Logger.getLogger(LoggingFilter.class).info(builder.toString());
    }


    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }
}
