package com.reewild.foodprint.foodprintapplication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SimpleApiKeyFilter extends OncePerRequestFilter {

    private final String apiKey;

    public SimpleApiKeyFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String headerKey = request.getHeader("X-API-KEY");
        if (apiKey != null && apiKey.equals(headerKey)) {
            filterChain.doFilter(request, response); // authorized
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // unauthorized
        }
    }
}
