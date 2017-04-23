package com.xti.spring.cloud.heroku.discovery.metadata;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MetadataFilter extends OncePerRequestFilter {

    private final int internalPort;

    public MetadataFilter(int internalPort) {
        this.internalPort = internalPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServerPort() != internalPort){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        filterChain.doFilter(request, response);
    }
}
