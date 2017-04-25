package com.xti.spring.cloud.heroku.discovery.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MetadataFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(MetadataFilter.class);

    private final int internalPort;

    public MetadataFilter(int internalPort) {
        this.internalPort = internalPort;
        log.info("Spring Cloud Heroku Metadata filter started at port: {}", internalPort);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServerPort() != internalPort){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        filterChain.doFilter(request, response);
    }
}
